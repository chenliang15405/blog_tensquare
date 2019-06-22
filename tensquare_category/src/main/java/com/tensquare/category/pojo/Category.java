package com.tensquare.category.pojo;

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
@Table(name="tb_category")
public class Category implements Serializable{

	@Id
	private String id;//分类ID

	private String categoryname;//分类名称
	private String state;//状态	0 可用 1 不可用
	private Long count;//使用数量
	private java.util.Date createdate;//创建时间
	private String userid;//创建人
	private String description;//描述
	private String color; //颜色

}
