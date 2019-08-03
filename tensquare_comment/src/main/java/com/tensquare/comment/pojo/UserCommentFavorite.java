package com.tensquare.comment.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @auther alan.chen
 * @time 2019/8/3 11:25 AM
 */
@Data
@Entity
@Table(name = "tb_user_comment_favorite")
public class UserCommentFavorite implements Serializable {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id; // 主键

	@Column(name = "comment_id")
	private Integer commentId; // 关联评论主键

	@Column(name = "user_id")
	private String userId; // 关联用户主键

	@Column(name = "ip")
	private String ip; // 用户ip

	@Column(name = "action")
	private String action; // 用户动作

	@Column(name = "create_date")
	private Date createDate; // 创建时间


}
