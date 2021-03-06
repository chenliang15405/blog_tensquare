package com.tensquare.blog.user.dao;

import com.tensquare.blog.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @auther alan.chen
 * @time 2019/6/3 8:10 PM
 */
public interface UserDao extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {

}
