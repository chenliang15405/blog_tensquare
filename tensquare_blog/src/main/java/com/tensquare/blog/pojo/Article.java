package com.tensquare.blog.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="tb_article")
public class Article implements Serializable{

	@Id
	private String id;//ID


	
	private String userid;//用户ID
	private String title;//标题
	private String summary;//摘要
	private LONGTEXT content;//文章正文
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
	private String private;//是否私密文章
	private String tag;//标签名称
	private String tagcolor;//标签颜色

	
	public String getId() {		
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public String getUserid() {		
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getTitle() {		
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getSummary() {		
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}

	public LONGTEXT getContent() {		
		return content;
	}
	public void setContent(LONGTEXT content) {
		this.content = content;
	}

	public String getImage() {		
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}

	public java.util.Date getCreatetime() {		
		return createtime;
	}
	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}

	public java.util.Date getUpdatetime() {		
		return updatetime;
	}
	public void setUpdatetime(java.util.Date updatetime) {
		this.updatetime = updatetime;
	}

	public String getIspublic() {		
		return ispublic;
	}
	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}

	public String getIstop() {		
		return istop;
	}
	public void setIstop(String istop) {
		this.istop = istop;
	}

	public Integer getVisits() {		
		return visits;
	}
	public void setVisits(Integer visits) {
		this.visits = visits;
	}

	public Integer getThumbup() {		
		return thumbup;
	}
	public void setThumbup(Integer thumbup) {
		this.thumbup = thumbup;
	}

	public Integer getComment() {		
		return comment;
	}
	public void setComment(Integer comment) {
		this.comment = comment;
	}

	public String getState() {		
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public Integer getCategorylid() {		
		return categorylid;
	}
	public void setCategorylid(Integer categorylid) {
		this.categorylid = categorylid;
	}

	public String getUrl() {		
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {		
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public String getPrivate() {		
		return private;
	}
	public void setPrivate(String private) {
		this.private = private;
	}

	public String getTag() {		
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getTagcolor() {		
		return tagcolor;
	}
	public void setTagcolor(String tagcolor) {
		this.tagcolor = tagcolor;
	}


	
}
