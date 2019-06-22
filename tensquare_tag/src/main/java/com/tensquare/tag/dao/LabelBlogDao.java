package com.tensquare.tag.dao;

import com.tensquare.tag.pojo.LabelBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @auther alan.chen
 * @time 2019/6/22 5:36 PM
 */
public interface LabelBlogDao extends JpaRepository<LabelBlog, Integer>, JpaSpecificationExecutor<LabelBlog> {


	@Query("from LabelBlog where blogid=:blogid")
	List<LabelBlog> findByBlogId(@Param("blogid") String blogId);
}
