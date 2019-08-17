package com.tensquare.blog.user.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * 博主消息通知vo
 * @auther alan.chen
 * @time 2019/8/13 10:01 PM
 */
@ToString
@Getter
@Setter
public class BloggerMessageVo implements Serializable {

	private Integer id; //主键id

	private String userName; //用户名称

	private String blogTitle;	//博客标题

	private String commentContent; //评论内容

	private Date createDate; // 创建时间

	private String type; // 类型

	private Integer status; // 状态

}
