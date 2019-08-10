package com.tensquare.blog.user.controller;

import com.tensquare.blog.user.entity.User;
import com.tensquare.blog.user.service.AdminUserService;
import com.tensquare.blog.user.service.UserService;
import com.tensquare.common.entity.PageResponse;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @auther alan.chen
 * @time 2019/6/3 8:07 PM
 */
@CrossOrigin
@RestController
@RequestMapping("/user")
@Api(tags = "用户Controller")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private AdminUserService adminUserService;

    /**
     * 根据 id 查询user
     *
     * @param id
     * @return
     */
    @ApiOperation(value = "通过id获取user信息", notes = "通过id获取user信息")
    @ApiImplicitParam(name = "id", value = "需要查询的id",required = true,dataType = "String",paramType = "path")
    @GetMapping("/{id}")
    public Response getUserById(@PathVariable String id) {
        return new Response(true, StatusCode.OK, "查询成功", userService.findById(id));
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @ApiOperation(value = "查询全部数据", notes = "查询全部user")
    @RequestMapping(method = RequestMethod.GET)
    public Response getUserDetailInfo() {
        return new Response(true, StatusCode.OK, "查询成功", userService.findAll());
    }

    /**
     * 查询admin的信息(可以跳过admin请求的校验)
     * 查新的blogger=Y 的admin信息，可以通过后台切换blogger
     *
     * @return
     */
    @ApiOperation(value = "查询admin数据", notes = "查询admin的信息")
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public Response getAdminUserInfo() {
        return new Response(true, StatusCode.OK, "查询成功", adminUserService.findBloggerInfo(null));
    }

    /**
     * 根据admin_id查询admin信息，给博客展示使用（跳过校验）
     * @return
     */
    @ApiOperation(value = "根据adminId查询admin数据", notes = "查询admin的信息")
    @RequestMapping(value = "/admin/{id}", method = RequestMethod.GET)
    public Response getAdminUserInfoById(@PathVariable("id") String id) {
        return new Response(true, StatusCode.OK, "查询成功", adminUserService.findBloggerInfo(id));
    }

    /**
     * 分页条件查询
     *
     * @param searchMap
     * @param page
     * @param size
     * @return
     */
    @ApiOperation(value = "分页条件查询", notes = "分页条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchMap", value = "查询条件", required = true,dataType = "User", dataTypeClass = User.class),
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int",paramType = "path"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "int",paramType = "path")
    })
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Response pageSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<User> pageList = userService.pageSearch(searchMap, page, size);
        return new Response(true, StatusCode.OK, "查询成功", new PageResponse<User>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 条件查询
     *
     * @param searchMap
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "条件查询")
    @ApiImplicitParam(name = "searchMap", value = "查询条件", required = true, dataType = "User",dataTypeClass = User.class)
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response search(@RequestBody Map searchMap) {
        return new Response(true, StatusCode.OK, "查询成功", userService.findSearch(searchMap));
    }


    /**
     * 增加
     * @param user
     * @return
     */
    @ApiOperation(value = "增加用户", notes = "")
    @RequestMapping(method = RequestMethod.POST)
    public Response add(@RequestBody User user) {
        userService.add(user);
        return new Response(true, StatusCode.OK, "增加成功");
    }

    /**
     * 保存评论用户
     * @param id
     * @return
     */
    @RequestMapping(value = "/comment/{id}", method = RequestMethod.POST)
    public Response createCommentUser(@PathVariable("id") String id){
        userService.createCommentUser(id);
        return new Response(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     * @param user
     * @param id
     * @return
     */
    @ApiOperation(value = "修改用户", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "需要修改的id",required = true,dataType = "String",paramType = "path")
    })
    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    public Response update(@RequestBody User user, @PathVariable String id) {
        user.setId(id);
        userService.updateById(user);
        return new Response(true, StatusCode.OK, "修改成功");
    }

    @ApiOperation(value = "删除用户", notes = "")
    @ApiImplicitParam(name = "id", value = "删除的用户id", required = true,dataType = "String",paramType = "path")
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    public Response delete(@PathVariable String id) {
        userService.deleteById(id);
        return new Response(true,StatusCode.OK , "删除成功");
    }




}
