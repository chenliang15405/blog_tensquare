package com.tensquare.category.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.category.pojo.Category;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface CategoryDao extends JpaRepository<Category,String>,JpaSpecificationExecutor<Category>{
	
}
