package com.tensquare.blog.user.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @auther alan.chen
 * @time 2019/6/7 11:55 AM
 */
@Data
@Entity
@Table(name = "tb_admin")
public class AdminUser implements Serializable {

    @Id
    private String id;//ID
    private String username; // 用户名称
    private String loginname;//登陆名称
    private String password;//密码
    private String state;//状态
    private String operation; //操作
    private Date create_date; //创建日期
    private Date update_date; // 更新日期
    private Date birthday; //生日
    private Date login_date; // 登录日期
    private String avatar; // 头像
    private String address;   // 地址
    private String login_site; //登录地点
    private String  email;  //邮箱

}
