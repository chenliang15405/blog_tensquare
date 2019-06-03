package com.tensquare.blog.user.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @auther alan.chen
 * @time 2019/6/3 11:11 PM
 */
@ApiModel
@Getter
@Setter
public class UserVo {

    @ApiModelProperty(value = "用户id", required = true)
    @NotBlank(message = "用户id不能为空")
    private Long id;

    @ApiModelProperty(value = "用户名称", required = true)
    @NotBlank(message = "用户名称不能为空")
    private String username;

    @ApiModelProperty(value = "用户昵称")
    private String nickname;

    @ApiModelProperty(value = "用户角色id", required = true)
    @NotBlank(message = "用户角色id不能为空")
    private Long roleId;

    @ApiModelProperty(value = "用户状态")
    private String status;



}
