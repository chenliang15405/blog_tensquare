package com.tensquare.blog.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tensquare.blog.client.CategoryClient;
import com.tensquare.blog.client.LabelClient;
import com.tensquare.blog.dao.ArticleDao;
import com.tensquare.blog.pojo.Article;
import com.tensquare.blog.vo.ArticleReqVo;
import com.tensquare.common.entity.Response;
import com.tensquare.common.utils.IdWorker;
import com.tensquare.common.vo.CategoryVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Slf4j
@Service //如果类加了这个注解，那么这个类里面的方法抛出异常，就会回滚，如果不配置rollbackFor属性,那么事物只会在遇到RuntimeException的时候才会回滚,加上rollbackFor=Exception.class,可以让事物在遇到非运行时异常时也回滚
@Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED) // 支持当前事务，如果当前没有事务，就新建一个事务
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private CategoryClient categoryClient;

	@Autowired
	private LabelClient labelClient;

	@Value("${blog.web-server-url}")
	private String BLOG_WEB_SERVER_URL;



	/**
	 * 点赞 发送消息到mq
	 * @param articleId
	 */
	public void thumbup(String articleId) {
		// 使用mq实现点赞，1. 防止高并发 2. 可以将点赞数量再存储到redis中方便查询
		try {
			//生产者指定exchange 与 routing-key 就可以发送到指定的queue
			rabbitTemplate.convertAndSend("blog_thumbup", "thumbup", articleId);
		} catch (AmqpException e) {
			log.error("点赞出现异常", e);
		}
	}

	/**
	 * 点赞
	 * @param articleId
	 */
	public void addThumbup(String articleId) {
		articleDao.addThumbup(articleId);
	}

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Article> findAll() {
		return articleDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Article> findSearch(Map whereMap, int page, int size) {
		Specification<Article> specification = createSpecification(whereMap);
		Sort sort = new Sort(Sort.Direction.DESC,"createtime");
		PageRequest pageRequest =  PageRequest.of(page-1, size, sort);
		// 倒序查询
		return articleDao.findAll(specification, pageRequest);
	}

	/**
	 * 归档（时间）列表查询
	 * @param pageSize
	 * @return
	 */
	public Page<Article> findArticleArchiveList(Integer page, Integer pageSize) {
		// 分页查询数据，并根据createDate排序
		Sort sort = new Sort(Sort.Direction.DESC,"createtime");
		PageRequest pageRequest = PageRequest.of(page-1, pageSize, sort);
		return articleDao.findAll(pageRequest);
	}

	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Article> findSearch(Map whereMap) {
		Specification<Article> specification = createSpecification(whereMap);
		return articleDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	@Cacheable(value = "article:pojo", key = "#id")
	public Article findById(String id) {
		// 需要对optional进行判断，如果不判断，则optional可能为null，报错 no such elementException
		Optional<Article> optional = articleDao.findById(id);
		return optional.orElse(null);
	}

	/**
	 * 增加
	 * @param vo
	 */
	public void add(ArticleReqVo vo) {
		Article article = new Article();
		String blogId = idWorker.nextId()+"";

		// 先查询是否存在该分类
		if(StringUtils.isBlank(vo.getCategoryid())) {
			handleCategory(vo); // java中的值传递和引用传递问题，虽然是值传递，但是传递的是对象的引用，所以可以改变对象属性的址值
		}
		handleLabelList(blogId, vo);

		// 在将vo copy到article中
		BeanUtils.copyProperties(vo, article);

		article.setId(blogId);
		article.setComment(0);
		article.setState(StringUtils.isBlank(vo.getState()) ? "0" : vo.getState());
		article.setThumbup(0);
		article.setCreatetime(new Date());
		article.setVisits(0);
		article.setIstop(StringUtils.isBlank(vo.getIstop()) ? "0" : vo.getIstop());
		article.setIspublic(vo.isIspublic() ? "0" : "1"); // 1：公开  0：私密文章
		article.setUrl(BLOG_WEB_SERVER_URL + blogId);
		article.setType(StringUtils.isBlank(vo.getType()) ? "0" : vo.getType());

		articleDao.save(article);
	}

	/**
	 * 修改
	 * @param vo
	 */
	// TODO cacheput 不能修改到数据
//	@CachePut(value = "article:pojo", key = "#article.id")
	@CacheEvict(value = "article:pojo", key = "#article.id")
	public void update(ArticleReqVo vo) {
		Article article = new Article();
		// 先判断是否已经存在该分类，如果不存在，则保存一个，如果存在，则使用该分类id
		// 通过mq异步保存一个新的分类
		handleCategory(vo);
		// 处理标签
		handleLabelList(vo.getId(), vo);
		BeanUtils.copyProperties(vo, article);

		articleDao.save(article);
	}

	/**
	 * 处理分类
	 * @param article
	 */
	private void handleCategory(ArticleReqVo article) {
		// TODO 后期重新写, 直接调用一次，如果没有查询到，则保存，如果查询到，则返回id
		if(StringUtils.isNotBlank(article.getCategoryName())) {
			Response response = categoryClient.findByCategoryname(article.getCategoryName());
			if(response.getData() != null) {
				Object o = JSON.toJSON(response.getData());
				CategoryVo categoryVo = JSON.parseObject(o.toString(), CategoryVo.class);
				article.setCategoryid(categoryVo.getId());
			} else {
				String categoryId = idWorker.nextId() + "";
				Map<String, String> map = Maps.newHashMap();
				map.put("categoryName", article.getCategoryName());
				map.put("categoryId", categoryId);

				// 通过mq异步发送消息到 category 服务
				rabbitTemplate.convertAndSend("blog_category", map);
				article.setCategoryid(categoryId);
			}
		}
	}

	/**
	 * 处理文章对应的标签列表
	 * @param vo
	 */
	 private void handleLabelList(String blogId, ArticleReqVo vo) {
		 try {
			 if(vo != null) {
				List<String> list = vo.getLabelList();
				// 将list传递到tag服务
				Map<String,Object> map = Maps.newHashMap();
				map.put("blogId", blogId);
				map.put("tagList", list);
				String json = JSON.toJSONString(map);
				labelClient.handleArticleLabel(json);
			}
		 } catch (Exception e) {
			 log.error("handleTagList error ", e);
		 }
	 }


	/**
	 * 删除
	 * @param id
	 */
	@CacheEvict(value = "article:pojo", key = "#id")
	public void deleteById(String id) {
		articleDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Article> createSpecification(Map searchMap) {

		return new Specification<Article>() {

			@Override
			public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (searchMap.get("id")!=null && !"".equals(searchMap.get("id"))) {
                	predicateList.add(cb.like(root.get("id").as(String.class), "%"+(String)searchMap.get("id")+"%"));
                }
                // 用户ID
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 标题
                if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
                	predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
                }
                // 摘要
                if (searchMap.get("summary")!=null && !"".equals(searchMap.get("summary"))) {
                	predicateList.add(cb.like(root.get("summary").as(String.class), "%"+(String)searchMap.get("summary")+"%"));
                }
                // 文章封面
                if (searchMap.get("image")!=null && !"".equals(searchMap.get("image"))) {
                	predicateList.add(cb.like(root.get("image").as(String.class), "%"+(String)searchMap.get("image")+"%"));
                }
                // 是否公开
                if (searchMap.get("ispublic")!=null && !"".equals(searchMap.get("ispublic"))) {
                	predicateList.add(cb.like(root.get("ispublic").as(String.class), "%"+(String)searchMap.get("ispublic")+"%"));
                }
                // 是否置顶
                if (searchMap.get("istop")!=null && !"".equals(searchMap.get("istop"))) {
                	predicateList.add(cb.like(root.get("istop").as(String.class), "%"+(String)searchMap.get("istop")+"%"));
                }
                // 审核状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
                // URL
                if (searchMap.get("url")!=null && !"".equals(searchMap.get("url"))) {
                	predicateList.add(cb.like(root.get("url").as(String.class), "%"+(String)searchMap.get("url")+"%"));
                }
                // 类型（原创、转载、翻译）
                if (searchMap.get("type")!=null && !"".equals(searchMap.get("type"))) {
                	predicateList.add(cb.like(root.get("type").as(String.class), "%"+(String)searchMap.get("type")+"%"));
                }
                // 标签名称
                /*if (searchMap.get("tag")!=null && !"".equals(searchMap.get("tag"))) {
                	predicateList.add(cb.like(root.get("tag").as(String.class), "%"+(String)searchMap.get("tag")+"%"));
                }*/
                // 标签颜色
                /*if (searchMap.get("tagcolor")!=null && !"".equals(searchMap.get("tagcolor"))) {
                	predicateList.add(cb.like(root.get("tagcolor").as(String.class), "%"+(String)searchMap.get("tagcolor")+"%"));
                }*/
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
