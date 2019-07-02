package com.tensquare.blog.user.dao;

import com.tensquare.blog.user.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
 * @auther alan.chen
 * @time 2019/6/7 3:25 PM
 */
public interface AdminUserDao  extends JpaRepository<AdminUser, String>, JpaSpecificationExecutor<AdminUser> {

    AdminUser findByLoginname(String loginname);

    @Query("FROM AdminUser a WHERE a.blogger='Y'")
	AdminUser findBloggerInfo();

}
