package com.tensquare.comment.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @auther alan.chen
 * @time 2019/7/20 12:38 PM
 */
@Data
@Entity
@Table(name = "tb_comment")
public class Comment implements Serializable {

	public static final Integer PASS_AUTH = 1; // 审核通过

	public static final Integer NOT_PASS = 0; // 审核不通过


	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id; // 主键id

	@Column(name = "user_id")
	private String userId; //tb_user表id

	@Column(name = "parent_id")
	private Integer parentId; // 关联评论父Id

	@Column(name = "blog_id")
	private String blogId; // 关联博客(tb_article)Id

	@Column(name = "content")
	private String content; // 评论内容

	@Column(name = "like_num")
	private Integer likeNum; // 点赞数量

	@Column(name = "dislike_num")
	private Integer dislikeNum; // 不喜欢数量

	@Column(name = "comment_level")
	private Integer commentLevel; // 评论层级

	@Column(name = "create_date")
	private Date createDate; // 创建时间

	@Column(name = "status")
	private Integer status; // 状态：1 审核通过 0 审核不通过

	@Column(name = "type")
	private Integer type; // 评论类型：0文章，1，阅读

}
