package com.tensquare.blog.user.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * blogger vo
 * @auther alan.chen
 * @time 2019/7/1 11:04 PM
 */
@ToString
@Getter
@Setter
public class BloggerVo implements Serializable {

	private String username; // 用户名称
	private Date createDate; //创建日期
	private Date birthday; //生日
	private String avatar; // 头像
	private String address;   // 地址
	private String email;  //邮箱
	private String motto; // 座右铭
	private String title; // title
}
