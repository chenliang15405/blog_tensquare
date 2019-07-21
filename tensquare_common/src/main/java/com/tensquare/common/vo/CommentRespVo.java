package com.tensquare.common.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @auther alan.chen
 * @time 2019/7/20 3:16 PM
 */
@Data
public class CommentRespVo {

	private int id; // 主键id

	private String userName;

	private String avatar;

	private String email;

	private String sex;

	private Integer parentId; // 关联评论父Id

	private String blogId; // 关联博客(tb_article)Id

	private String content; // 评论内容

	private Integer likeNum; // 点赞数量

	private Integer dislikeNum; // 不喜欢数量

	private Integer commentLevel; // 评论层级

	private Date createDate; // 创建时间

	private List<CommentRespVo> childrens; // 子评论列表

}
