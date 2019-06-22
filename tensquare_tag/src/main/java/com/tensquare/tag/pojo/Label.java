package com.tensquare.tag.pojo;

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
@Table(name="tb_label")
public class Label implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;//标签ID

	private String labelname;//标签名称
	private String state;//状态
	private Long count;//使用数量
	private String recommend;//是否推荐
	private String color;//标签颜色


	
}
