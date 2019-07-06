package com.tensquare.blog.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 图片上传记录
 *
 * @auther alan.chen
 * @time 2019/7/6 11:51 AM
 */
@ToString
@Getter
@Setter
@Entity
@Table(name = "tb_images")
public class Images implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;	// 图片id

	@Column(name = "image_origin_name")
	private String ImageOriginName;	// 图片原始名称

	@Column(name = "image_name")
	private String ImageName; // 图片名称

	@Column(name = "image_path")
	private String ImagePath; // 图片上传路径

	@Column(name = "path")
	private String path; // 图片访问路径

	@Column(name = "create_time")
	private Date createTime; // 上传时间


}
