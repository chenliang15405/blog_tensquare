package com.tensquare.blog.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import com.tensquare.blog.client.CategoryClient;
import com.tensquare.blog.dao.ArticleDao;
import com.tensquare.blog.pojo.Article;
import com.tensquare.common.entity.Response;
import com.tensquare.common.utils.IdWorker;
import com.tensquare.common.vo.CategoryVo;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private IdWorker idWorker;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Autowired
	private CategoryClient categoryClient;


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
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return articleDao.findAll(specification, pageRequest);
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
	public Article findById(String id) {
		// 需要对optional进行判断，如果不判断，则optional可能为null，报错 no such elementException
		Optional<Article> optional = articleDao.findById(id);
		return optional.orElse(null);
	}

	/**
	 * 增加
	 * @param article
	 */
	public void add(Article article) {
		// 先查询是否存在该分类
		handleCategory(article); // java中的值传递和引用传递问题，虽然是值传递，但是传递的是对象的引用，所以可以改变对象属性的址值

		article.setId( idWorker.nextId()+"" );
		articleDao.save(article);
	}

	/**
	 * 修改
	 * @param article
	 */
	public void update(Article article) {
		// 先判断是否已经存在该分类，如果不存在，则保存一个，如果存在，则使用该分类id
		// 通过mq异步保存一个新的分类
		handleCategory(article);
		articleDao.save(article);
	}

	/**
	 * 处理分类
	 * @param article
	 */
	private void handleCategory(Article article) {
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


	/**
	 * 删除
	 * @param id
	 */
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
                if (searchMap.get("tag")!=null && !"".equals(searchMap.get("tag"))) {
                	predicateList.add(cb.like(root.get("tag").as(String.class), "%"+(String)searchMap.get("tag")+"%"));
                }
                // 标签颜色
                if (searchMap.get("tagcolor")!=null && !"".equals(searchMap.get("tagcolor"))) {
                	predicateList.add(cb.like(root.get("tagcolor").as(String.class), "%"+(String)searchMap.get("tagcolor")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
