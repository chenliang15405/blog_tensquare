package com.tensquare.category.pojo;

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
@Table(name="tb_category")
public class Category implements Serializable{

	@Id
	private Integer id;//分类ID


	
	private String categoryname;//分类名称
	private String state;//状态
	private Long count;//使用数量
	private java.util.Date createdate;//创建时间
	private String userid;//创建人
	private String description;//描述

	
	public Integer getId() {		
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryname() {		
		return categoryname;
	}
	public void setCategoryname(String categoryname) {
		this.categoryname = categoryname;
	}

	public String getState() {		
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}

	public Long getCount() {		
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}

	public java.util.Date getCreatedate() {		
		return createdate;
	}
	public void setCreatedate(java.util.Date createdate) {
		this.createdate = createdate;
	}

	public String getUserid() {		
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getDescription() {		
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	
}
