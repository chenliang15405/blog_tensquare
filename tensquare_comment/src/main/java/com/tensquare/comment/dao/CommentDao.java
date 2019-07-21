package com.tensquare.comment.dao;

import com.tensquare.comment.pojo.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @auther alan.chen
 * @time 2019/7/20 1:01 PM
 */
public interface CommentDao extends JpaRepository<Comment,Integer>,JpaSpecificationExecutor<Comment> {


	@Query("from Comment where parentId=:parentId and status=1 order by createDate")
	List<Comment> findByParentId(@Param("parentId") Integer id);

}
