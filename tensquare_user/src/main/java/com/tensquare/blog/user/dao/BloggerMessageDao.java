package com.tensquare.blog.user.dao;

import com.tensquare.blog.user.entity.BloggerMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @auther alan.chen
 * @time 2019/7/23 11:32 PM
 */
public interface BloggerMessageDao extends JpaRepository<BloggerMessage, Integer>, JpaSpecificationExecutor<BloggerMessage> {
}
