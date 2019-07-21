package com.tensquare.comment.service;

import com.tensquare.comment.dao.CommentDao;
import com.tensquare.comment.pojo.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.util.ArrayList;
import java.util.List;

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



	/**
	 * 根据博客id 分页查询
	 * @param blogId
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Comment> pageByBlogId(String blogId, int page, int size) {
		Specification<Comment> specification = createSpecification(blogId);
		Sort sort = new Sort(Sort.Direction.DESC, "createDate");
		PageRequest pageRequest =  PageRequest.of(page-1, size, sort);
		Page<Comment> pageList = commentDao.findAll(specification, pageRequest);
		return pageList;
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
	public void save(Comment comment) {
		commentDao.save(comment);
	}

	/**
	 * 删除评论
	 * @param id
	 */
	public void deleteById(Integer id) {
		commentDao.deleteById(id);
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
				predicateList.add(cb.equal(root.get("status").as(Integer.class), Comment.PUBLIC_STATUS));
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));
			}
		};
	}

}
