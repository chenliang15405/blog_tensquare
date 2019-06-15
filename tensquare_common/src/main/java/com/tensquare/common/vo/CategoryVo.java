package com.tensquare.common.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @auther alan.chen
 * @time 2019/6/11 9:07 PM
 */
@Getter
@Setter
@ToString
public class CategoryVo implements Serializable {

    private String id;//分类ID
    private String categoryname;//分类名称
    private String state;//状态
    private Long count;//使用数量
    private Date createdate;//创建时间
    private String userid;//创建人
    private String description;//描述


}
