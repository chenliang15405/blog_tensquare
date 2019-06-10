package com.tensquare.blog.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
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
	private String image;//文章封面
	private java.util.Date createtime;//发表日期
	private java.util.Date updatetime;//修改日期
	private String ispublic;//是否公开
	private String istop;//是否置顶
	private Integer visits;//浏览量
	private Integer thumbup;//点赞数
	private Integer comment;//评论数
	private String state;//审核状态
	private Integer categorylid;//所属类型
	private String url;//URL
	private String type;//类型（原创、转载、翻译）
	private String tag;//标签名称
	private String tagcolor;//标签颜色


}
