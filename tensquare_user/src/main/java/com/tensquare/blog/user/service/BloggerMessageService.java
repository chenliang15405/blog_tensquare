package com.tensquare.blog.user.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tensquare.blog.user.client.BlogClient;
import com.tensquare.blog.user.client.CommentClient;
import com.tensquare.blog.user.dao.BloggerMessageDao;
import com.tensquare.blog.user.entity.BloggerMessage;
import com.tensquare.blog.user.entity.User;
import com.tensquare.blog.user.vo.BloggerMessageVo;
import com.tensquare.common.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @auther alan.chen
 * @time 2019/7/23 11:31 PM
 */
@Slf4j
@Service
@Transactional
public class BloggerMessageService {

	@Autowired
	private BloggerMessageDao bloggerMessageDao;

	@Autowired
	private BlogClient blogClient;
	@Autowired
	private CommentClient commentClient;
	@Autowired
	private UserService userService;

	private static final Integer NOT_READ = 0;

	/**
	 * 保存通知消息
	 * @param bloggerMessage
	 */
	public void save(BloggerMessage bloggerMessage) {
		bloggerMessage.setStatus(NOT_READ);
		bloggerMessage.setCreateDate(new Date());
		bloggerMessageDao.save(bloggerMessage);
	}

	/**
	 * 根据状态查询通知消息
	 * @param status
	 * @return
	 */
	public List<BloggerMessageVo> findBloggerMessageByStatus(Integer status) {
		List<BloggerMessage> list = null;
		if(status != null) {
			list = bloggerMessageDao.findBloggerMessageByStatus(status);
		} else {
			list = bloggerMessageDao.findAll(new Sort(Sort.Direction.DESC,"createDate"));
		}
		List<BloggerMessageVo> data = Lists.newArrayList();
		if(list != null && list.size() > 0) {
			list.forEach(item -> {
				BloggerMessageVo vo = new BloggerMessageVo();
				vo.setId(item.getId());
				vo.setType(item.getType());
				vo.setStatus(item.getStatus());
				vo.setCreateDate(item.getCreateDate());
				// 查询博客名称
				settingPropsWithRemoteClient(vo, item);
				data.add(vo);
			});
		}
		return data;
	}

	/**
	 * 更新通知消息
	 * @param id
	 * @param status
	 * @return
	 */
	public boolean updateBloggerMessageStatus(Integer id, Integer status) {
		try {
			Optional<BloggerMessage> optional = bloggerMessageDao.findById(id);
			BloggerMessage bloggerMessage = optional.orElse(null);
			if(bloggerMessage != null) {
				bloggerMessage.setStatus(status);
				bloggerMessageDao.save(bloggerMessage);
				return true;
			}
		} catch (Exception e) {
			log.error("更新通知消息异常", e);
		}
		return false;
	}

	/**
	 * 设置属性
	 * @param vo
	 */
	private void settingPropsWithRemoteClient(BloggerMessageVo vo, BloggerMessage item) {
		// 查询博客标题
		try {
			Response response = blogClient.getAricleById(item.getBlogId());
			if(response.getData() != null) {
				Object json = JSON.toJSON(response.getData());
				Map<String,String> map = (Map<String, String>) JSON.parse(String.valueOf(json));
				vo.setBlogTitle(map.get("title"));
			}
			// 查询评论内容
			Response response1 = commentClient.findById(item.getCommentId());
			if(response1.getData() != null) {
				Object json = JSON.toJSON(response1.getData());
				Map<String,String> map = (Map<String, String>) JSON.parse(String.valueOf(json));
				vo.setCommentContent(map.get("content"));
			}
			// 查询用户
			User user = userService.findById(item.getUserId());
			if(user != null) {
				vo.setUserName(user.getNickname());
			}
		} catch (Exception e) {
			log.error("【FeignClient】查询博主通知信息异常 ", e);
		}
	}

}
