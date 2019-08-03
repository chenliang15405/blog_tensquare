package com.tensquare.blog.user.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
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
	private Integer id;

	@Column(name = "user_id")
	private String userId;

	@Column(name = "blog_id")
	private String blogId;

	@Column(name = "comment_id")
	private Integer commentId;

	@Column(name = "create_date")
	private Date createDate;

	@Column(name = "status")
	private Integer status;


}
