package com.tensquare.tag.service;

import com.tensquare.common.utils.IdWorker;
import com.tensquare.tag.dao.TagDao;
import com.tensquare.tag.pojo.Label;
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

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
@Transactional
public class TagService {

	@Autowired
	private TagDao tagDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Label> findAll() {
		return tagDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Label> findSearch(Map whereMap, int page, int size) {
		Specification<Label> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return tagDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Label> findSearch(Map whereMap) {
		Specification<Label> specification = createSpecification(whereMap);
		return tagDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Label findById(Integer id) {
		return tagDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param tag
	 */
	public void add(Label tag) {
//		tag.setId( idWorker.nextId()+"" );
		tagDao.save(tag);
	}

	/**
	 * 修改
	 * @param tag
	 */
	public void update(Label tag) {
		tagDao.save(tag);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(Integer id) {
		tagDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Label> createSpecification(Map searchMap) {

		return new Specification<Label>() {

			@Override
			public Predicate toPredicate(Root<Label> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 标签名称
                if (searchMap.get("labelname")!=null && !"".equals(searchMap.get("labelname"))) {
                	predicateList.add(cb.like(root.get("labelname").as(String.class), "%"+(String)searchMap.get("labelname")+"%"));
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
