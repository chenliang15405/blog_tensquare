package com.tensquare.blog.user.entity;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 前端页面用户实体类
 *
 * @auther alan.chen
 * @time 2019/6/3 8:11 PM
 */
@Data
@Entity
@Table(name = "tb_user")
public class User implements Serializable {

    @Id
    private String id;//ID
    private String email;//E-Mail
    private String followme; // 是否关注我的动态
    private String nickname;//昵称
    private String avatar;//头像
    private Date regdate;//注册日期
    private Date updatedate;//修改日期
    private Date lastdate;//最后登陆日期
    private Long online;//在线时长（分钟）
    private String interest;//兴趣
    private String mobile;//手机号码
    private String password;//密码
    private String sex;//性别
    private Date birthday;//出生年月日
    private String personality;//个性
    private Integer fanscount;//粉丝数
    private Integer followcount;//关注数
    private String github;  //github地址
    private String csdn;    // csdn地址
    private String other_account; //其他账号


}
