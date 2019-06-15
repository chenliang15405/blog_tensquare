package com.tensquare.category.service;

import com.tensquare.category.dao.CategoryDao;
import com.tensquare.category.pojo.Category;
import com.tensquare.common.utils.IdWorker;
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
import java.util.*;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class CategoryService {

	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Category> findAll() {
		return categoryDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Category> findSearch(Map whereMap, int page, int size) {
		Specification<Category> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return categoryDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Category> findSearch(Map whereMap) {
		Specification<Category> specification = createSpecification(whereMap);
		return categoryDao.findAll(specification);
	}

	/**
	 *  根据分类名称查询
	 *
	 * @param categoryName
	 * @return
	 */
	public Category findByCategoryname(String categoryName) {
		// mysql默认查询不区分大小写
		List<Category> list = categoryDao.findByCategoryname(categoryName);
		return list != null? list.get(0) : null;
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Category findById(String id) {
		Optional<Category> optional = categoryDao.findById(id);
		return optional.isPresent()? optional.get(): null;
	}

	/**
	 * 增加
	 * @param category
	 */
	public void add(Category category) {
		category.setId( idWorker.nextId()+"" );
		category.setCreatedate(new Date());
		category.setCount(0L);
		category.setState("0");
		categoryDao.save(category);
	}

	/**
	 * 修改
	 * @param category
	 */
	public void update(Category category) {
		categoryDao.save(category);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		categoryDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Category> createSpecification(Map searchMap) {

		return new Specification<Category>() {

			@Override
			public Predicate toPredicate(Root<Category> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 分类名称
                if (searchMap.get("categoryname")!=null && !"".equals(searchMap.get("categoryname"))) {
                	predicateList.add(cb.like(root.get("categoryname").as(String.class), "%"+(String)searchMap.get("categoryname")+"%"));
                }
                // 状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
                // 创建人
                if (searchMap.get("userid")!=null && !"".equals(searchMap.get("userid"))) {
                	predicateList.add(cb.like(root.get("userid").as(String.class), "%"+(String)searchMap.get("userid")+"%"));
                }
                // 描述
                if (searchMap.get("description")!=null && !"".equals(searchMap.get("description"))) {
                	predicateList.add(cb.like(root.get("description").as(String.class), "%"+(String)searchMap.get("description")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
