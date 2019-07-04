package com.tensquare.blog.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @auther alan.chen
 * @time 2019/7/3 10:28 PM
 */
@Data
public class ArticleReqVo implements Serializable {

	private String id;//ID

	private String userid;//用户ID
	private String title;//标题
	private String summary;//摘要

	private String content;//文章正文
	private String image;//文章封面图url
	private java.util.Date createtime;//发表日期
	private java.util.Date updatetime;//修改日期
	private String ispublic;//是否公开
	private String istop;//是否置顶
	private Integer visits;//浏览量
	private Integer thumbup;//点赞数
	private Integer comment;//评论数
	private String state;//审核状态 0: 已发布 1:草稿 2:待审核 3: 垃圾箱
	private String categoryid;//所属类型
	private String categoryName;//分类名称
	private List<String> labelList; // 标签列表
	private String url;//URL
	private String type;//类型（原创:0、转载:1、翻译:2）

}
