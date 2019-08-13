package com.tensquare.blog.user.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 博主消息通知实体类
 * @auther alan.chen
 * @time 2019/7/23 11:26 PM
 */
@Data
@Entity
@Table(name = "TB_BLOGGER_MESSAGE")
public class BloggerMessage implements Serializable {


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; //主键id

	@Column(name = "user_id")
	private String userId; //用户id

	@Column(name = "blog_id")
	private String blogId;	//博客id

	@Column(name = "comment_id")
	private Integer commentId; //评论id

	@Column(name = "create_date")
	private Date createDate; // 创建时间

	@Column(name = "type")
	private String type; // 类型

	@Column(name = "status")
	private Integer status; // 状态


}
