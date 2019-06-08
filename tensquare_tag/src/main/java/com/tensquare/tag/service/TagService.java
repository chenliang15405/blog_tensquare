package com.tensquare.tag.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import com.tensquare.tag.dao.TagDao;
import com.tensquare.tag.pojo.Tag;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class TagService {

	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Tag> findAll() {
		return tagDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Tag> findSearch(Map whereMap, int page, int size) {
		Specification<Tag> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return tagDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Tag> findSearch(Map whereMap) {
		Specification<Tag> specification = createSpecification(whereMap);
		return tagDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Tag findById(String id) {
		return tagDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param tag
	 */
	public void add(Tag tag) {
		tag.setId( idWorker.nextId()+"" );
		tagDao.save(tag);
	}

	/**
	 * 修改
	 * @param tag
	 */
	public void update(Tag tag) {
		tagDao.save(tag);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		tagDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Tag> createSpecification(Map searchMap) {

		return new Specification<Tag>() {

			@Override
			public Predicate toPredicate(Root<Tag> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 标签名称
                if (searchMap.get("tagname")!=null && !"".equals(searchMap.get("tagname"))) {
                	predicateList.add(cb.like(root.get("tagname").as(String.class), "%"+(String)searchMap.get("tagname")+"%"));
                }
                // 状态
                if (searchMap.get("state")!=null && !"".equals(searchMap.get("state"))) {
                	predicateList.add(cb.like(root.get("state").as(String.class), "%"+(String)searchMap.get("state")+"%"));
                }
                // 是否推荐
                if (searchMap.get("recommend")!=null && !"".equals(searchMap.get("recommend"))) {
                	predicateList.add(cb.like(root.get("recommend").as(String.class), "%"+(String)searchMap.get("recommend")+"%"));
                }
                // 标签颜色
                if (searchMap.get("color")!=null && !"".equals(searchMap.get("color"))) {
                	predicateList.add(cb.like(root.get("color").as(String.class), "%"+(String)searchMap.get("color")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
