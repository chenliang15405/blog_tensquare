package com.tensquare.comment.service;

import com.google.common.collect.Maps;
import com.tensquare.comment.client.UserClient;
import com.tensquare.comment.config.BusinessException;
import com.tensquare.comment.dao.CommentDao;
import com.tensquare.comment.pojo.Comment;
import com.tensquare.comment.pojo.UserCommentFavorite;
import com.tensquare.comment.utils.RedisUtil;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import com.tensquare.common.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * @auther alan.chen
 * @time 2019/7/20 1:01 PM
 */
@Slf4j
@Service
@Transactional
public class CommentService {

	@Autowired
	private CommentDao commentDao;

	@Autowired
	private UserClient userClient;

	@Autowired
	private UserCommentFavoriteService userCommentFavoriteService;

	@Autowired
	private IdWorker idWorker;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private RedisUtil redisUtil;

	@Value("${comment.user.default.userId}")
	private String defaultUserId;

	private static final String COMMENT_LIKE_NUM_KEY = "comment:like:num";
	private static final String COMMENT_DISLIKE_NUM_KEY = "comment:dislike:num";

	private static final String COMMENT_LIKE = "like";
	private static final String COMMENT_DISLIKE = "dislike";

	private static final String TYPE_FAVORITE = "favorite_comment";
	private static final String TYPE_COMMENT = "comment";



	/**
	 * 根据博客id 分页查询
	 * @param blogId
	 * @param page
	 * @param size
	 * @return
	 */
	@Cacheable(value = "comment:page:list", key = "#blogId")
	public Page<Comment> pageByBlogId(String blogId, int page, int size) {
		Specification<Comment> specification = createSpecification(blogId);
		Sort sort = new Sort(Sort.Direction.DESC, "createDate");
		PageRequest pageRequest =  PageRequest.of(page-1, size, sort);
		Page<Comment> pageList = commentDao.findAll(specification, pageRequest);
		return pageList;
	}

	/**
	 * 点赞评论
	 * @param id
	 */
	public void favoriteComment(Integer id, String type, String ip) throws BusinessException {
		// 跟新用户点赞动作表数据
		UserCommentFavorite usercomment = userCommentFavoriteService.findByCommentIdAndIp(id, ip);
		if(usercomment != null && type.equals(usercomment.getAction())) {
			if(COMMENT_LIKE.equals(type)) {
				throw new BusinessException("已经点赞过啦");
			} else if(COMMENT_DISLIKE.equals(type)) {
				throw new BusinessException("已经踩过了哦");
			}
		}

		// 更新评论表数据 start
		// TODO 目前保存点赞数据到数据库，需要使用redis处理
		Optional<Comment> optional = commentDao.findById(id);
		Comment comment = optional.orElse(null);
		if(comment != null) {
			if(COMMENT_LIKE.equals(type)) {
				if(usercomment != null && COMMENT_DISLIKE.equals(usercomment.getAction())) {
					Integer dislikeNum = comment.getDislikeNum();
					comment.setDislikeNum(dislikeNum != null? --dislikeNum : 0);
				}
				Integer likeNum = comment.getLikeNum();
				comment.setLikeNum(likeNum != null ? ++likeNum : 1);
			} else if(COMMENT_DISLIKE.equals(type)) {
				if(usercomment != null && COMMENT_LIKE.equals(usercomment.getAction())) {
					Integer likeNum = comment.getLikeNum();
					comment.setLikeNum(likeNum != null? --likeNum : 0);
				}
				Integer dislikeNum = comment.getDislikeNum();
				comment.setDislikeNum(dislikeNum != null ? ++dislikeNum : 1);
			}
			commentDao.save(comment);

			// 发送mq消息，保存数据库，通知消息
			log.info("【点赞模块】点赞发送mq消息 start");
			sendMessageToBloger(comment.getUserId(), comment.getBlogId(), comment.getId(), TYPE_FAVORITE);
			log.info("【点赞模块】点赞发送mq消息 end");
		}
		// 更新评论数据 end


		if(usercomment == null) {
			usercomment = new UserCommentFavorite();
		}
		usercomment.setCommentId(id);
		usercomment.setAction(type);
		usercomment.setCreateDate(new Date());
		usercomment.setIp(ip);
		userCommentFavoriteService.save(usercomment);

		// 从redis中查询数量，并自增，然后mq处理 数据库增加和发送消息给博主
		/*String numStr = null;
		Integer num = null;
		if(COMMENT_LIKE.equals(type)) {
			numStr = redisUtil.get(COMMENT_LIKE_NUM_KEY + id);
			num = (numStr == null || "".equals(numStr)) ? 0 : Integer.valueOf(numStr);
			redisUtil.set(COMMENT_LIKE_NUM_KEY + id, ++num);
		}
		if(COMMENT_DISLIKE.equals(type)) {
			numStr = redisUtil.get(COMMENT_DISLIKE_NUM_KEY + id);
			num = (numStr == null || "".equals(numStr)) ? 0 : Integer.valueOf(numStr);
			redisUtil.set(COMMENT_DISLIKE_NUM_KEY + id, ++num);
		}
		// 使用ip作唯一性校验， 或者目前不做校验

		Optional<Comment> optional = commentDao.findById(id);
		Comment comment = optional.orElse(null);
		if(comment != null) {
			Integer num1 = comment.getLikeNum();
			comment.setLikeNum(num != null ? ++num : 0);
			commentDao.save(comment);
		}*/

	}

	/**
	 * 查询子列表
	 * @param id
	 * @return
	 */
	public List<Comment> findByParentId(Integer id) {
		List<Comment> list = commentDao.findByParentId(id);
		return list;
	}

	/**
	 * 保存评论
	 * @param comment
	 */
	@CacheEvict(value = "comment:page:list", key = "#comment.getBlogId()")
	public void save(Comment comment) {
		comment.setCreateDate(new Date());
		comment.setDislikeNum(0);
		comment.setLikeNum(0);
		// TODO 默认为通过，后面可以作为智能判断是否有某些词语
		comment.setStatus(Comment.PASS_AUTH);

		if(StringUtils.isBlank(comment.getUserId())) {
			String userId = idWorker.nextId() + "";
			// 创建用户，并返回id
			String createUserId = createCommentUser(userId, 1);
			comment.setUserId(createUserId);
		}
		comment = commentDao.save(comment);

		// 将该评论攒点和非点赞数存到redis
		Integer id = comment.getId();
		redisUtil.set(COMMENT_LIKE_NUM_KEY + id, 0);
		redisUtil.set(COMMENT_DISLIKE_NUM_KEY + id, 0);

		//发送mq消息
		log.info("【评论模块】新增评论发送MQ消息：Start");
		sendMessageToBloger(comment.getUserId(), comment.getBlogId(), comment.getId(), TYPE_COMMENT);
		log.info("【评论模块】新增评论发送MQ消息：End");
	}

	/**
	 * 根据id查询评论
	 * @param id
	 * @return
	 */
	public Comment findById(Integer id) {
		Optional<Comment> optional = commentDao.findById(id);
		return optional.orElse(null);
	}

	/**
	 * 调用user微服务创建用户，如果调用失败3次，则使用默认的userId
	 * @param userId
	 */
	private String createCommentUser(String userId, int count) {
		if(count > 3) {
			return defaultUserId;
		}
		Response response = userClient.createCommentUser(userId);
		if(response.getCode() != StatusCode.OK) {
			return createCommentUser(userId, ++count);
		}
		return userId;
	}

	/**
	 * 删除评论,TODO 删除缓存
	 * @param id
	 */
	public void deleteById(Integer id) {
		commentDao.deleteById(id);
	}

	/**
	 * 回复评论
	 * @param commentId
	 * @param replyContent
	 */
	public void replyComment(Integer commentId, String replyContent) throws BusinessException {
		// id保存父id
		Optional<Comment> optional = commentDao.findById(commentId);
		Comment parentComment = optional.orElse(null);
		if(parentComment == null) {
			throw new BusinessException("回复的评论不存在，数据异常: 回复的评论ID：" + commentId);
		}
		// 保存回复评论
		Comment comment = new Comment();
		// 评论的层数只展示3层，如果是第三层的回复，则设置parentId为第3层的parentId
		if(parentComment.getCommentLevel() == 3) {
			comment.setParentId(parentComment.getParentId());
		} else {
			comment.setParentId(commentId);
		}
		comment.setContent(replyContent);
		comment.setCommentLevel(parentComment.getCommentLevel() == 3 ? 3 : parentComment.getCommentLevel() + 1);
		comment.setBlogId(parentComment.getBlogId());

		// 保存评论，并发送消息
		save(comment);
	}

	/**
	 * 发送mq消息通知博主
	 * @param createUserId
	 * @param blogId
	 * @param commentId
	 */
	private void sendMessageToBloger(String createUserId, String blogId, Integer commentId, String type) {
		// 通过 rabbbitmq发送消息，通知博主有信息的消息提示
		Map<String,Object> map = Maps.newHashMap();
		map.put("userId", createUserId);
		map.put("blogId", blogId);
		map.put("commentId", commentId);
		map.put("type", type);
		rabbitTemplate.convertAndSend("user_comment_message_channel", map);
	}

	/**
	 * 动态条件构建
	 * @return
	 */
	private Specification<Comment> createSpecification(String blogId) {
		return new Specification<Comment>() {
			@Override
			public Predicate toPredicate(Root<Comment> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// 条件
				if (blogId !=null && !"".equals(blogId)) {
					predicateList.add(cb.equal(root.get("blogId").as(String.class), blogId));
				}
				predicateList.add(cb.isNull((root.get("parentId").as(String.class))));
				// 状态
				predicateList.add(cb.equal(root.get("status").as(Integer.class), Comment.PASS_AUTH));
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

}
