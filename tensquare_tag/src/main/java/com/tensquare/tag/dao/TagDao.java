package com.tensquare.tag.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.tensquare.tag.pojo.Tag;
/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface TagDao extends JpaRepository<Tag,Integer>,JpaSpecificationExecutor<Tag>{
	
}
