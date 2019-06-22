package com.tensquare.tag.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @auther alan.chen
 * @time 2019/6/22 6:37 PM
 */
@Data
public class LabelBlogVo implements Serializable {

	private String labelname; // 标签名称
	private String color;//标签颜色

}
