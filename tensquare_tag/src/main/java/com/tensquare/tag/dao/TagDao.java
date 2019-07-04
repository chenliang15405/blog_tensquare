package com.tensquare.tag.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.tag.pojo.Label;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface TagDao extends JpaRepository<Label,Integer>,JpaSpecificationExecutor<Label>{

	@Query("from Label where labelname = :name")
	List<Label> findByName(@Param("name") String name);
}
