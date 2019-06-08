package com.tensquare.tag.pojo;

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
@Table(name="tb_tag")
public class Tag implements Serializable{

	@Id
	private Integer id;//标签ID


	
	private String tagname;//标签名称
	private String state;//状态
	private Long count;//使用数量
	private String recommend;//是否推荐
	private String color;//标签颜色

	
	public Integer getId() {		
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getTagname() {		
		return tagname;
	}
	public void setTagname(String tagname) {
		this.tagname = tagname;
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

	public String getRecommend() {		
		return recommend;
	}
	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public String getColor() {		
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}


	
}
