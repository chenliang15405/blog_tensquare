package com.tensquare.blog.dao;

import com.tensquare.blog.pojo.Images;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @auther alan.chen
 * @time 2019/7/6 12:11 PM
 */
public interface ImageFileDao extends JpaRepository<Images,Integer>,JpaSpecificationExecutor<Images> {
}
