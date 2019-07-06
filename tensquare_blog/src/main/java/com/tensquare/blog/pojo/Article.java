package com.tensquare.blog.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 * @author Administrator
 *
 */
@Data
@Entity
@Table(name="tb_article")
public class Article implements Serializable{

	@Id
	private String id;//ID

	private String userid;//用户ID
	private String title;//标题
	private String summary;//摘要

	@Lob // 申明属性对应的数据库字段为一个大文本类
	@Basic(fetch = FetchType.LAZY)	//	大数据字段最好再加上延迟加载
	private String content;//文章正文
	@Lob
	@Basic(fetch = FetchType.LAZY)
	private String textcontent; // 文章纯文本
	private String image;//文章封面图url
	private Date createtime;//发表日期
	private Date updatetime;//修改日期
	private String ispublic;//是否公开
	private String istop;//是否置顶
	private Integer visits;//浏览量
	private Integer thumbup;//点赞数
	private Integer comment;//评论数
	private String state;//审核状态 0: 已发布 1:草稿 2:待审核 3: 垃圾箱
	private String categoryid;//所属类型
	private String categoryName;//分类名称
	private String url;//URL
	private String type;//类型（原创:0、转载:1、翻译:2）
//	private String labelname;//标签名称
//	private String labelcolor;//标签颜色 使用中间表关联，不需要保存标签了


}
