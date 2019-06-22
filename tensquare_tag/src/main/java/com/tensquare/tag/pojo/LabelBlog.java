package com.tensquare.tag.pojo;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @auther alan.chen
 * @time 2019/6/22 5:17 PM
 */
@Data
@Entity
@Table(name = "tb_blog_label")
public class LabelBlog implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String blogid;
	private Integer labelid;

}
