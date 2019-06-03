package com.tensquare.blog.user.controller;

import com.tensquare.blog.user.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @auther alan.chen
 * @time 2019/6/3 8:07 PM
 */
@RestController
@RequestMapping("/user")
@Api(tags = "用户Controller")
public class UserController {

    @Autowired
    private UserService userService;


    @ApiOperation(value = "通过id获取user信息", notes = "通过id获取user信息")
    @GetMapping("/get/{id}")
    public String getUserById(@PathVariable Long id) {
        /*if (bindingResult.hasErrors()) {
            return new ResponseBody(CodeConstant.GENERAL_ERROR, BindingResultUtil.getBindingResultErrMsg(bindingResult));
        }*/
        return "getUserById";
    }

    @ApiOperation(value = "根据用户id获取用户详细信息", notes = "根据用户id获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户唯一标示", required = true, dataType = "Long", paramType = "query")
    @PostMapping("/get/detail")
    public String getUserDetailInfo(@RequestParam Long id) {
        return "getUserDetailInfo";
    }

}
