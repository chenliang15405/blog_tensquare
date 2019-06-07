package com.tensquare.blog.user.controller;

import com.tensquare.blog.user.entity.AdminUser;
import com.tensquare.blog.user.service.AdminUserService;
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
 * 后台系统管理员Controller
 *
 * @auther alan.chen
 * @time 2019/6/7 3:24 PM
 */
@Api(tags = "管理员Controller")
@CrossOrigin
@RestController
@RequestMapping("/admin")
public class AdminUserController {

    @Autowired
    private AdminUserService adminUserService;


    /**
     * 查询全部数据
     *
     * @return
     */
    @ApiOperation(value = "查询所有的管理员信息", notes = "")
    @RequestMapping(method = RequestMethod.GET)
    public Response findAll() {
        return new Response(true, StatusCode.OK, "查询成功", adminUserService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @ApiOperation(value = "根据id查询管理员信息")
    @ApiImplicitParam(name = "id", value = "管理员id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response findById(@PathVariable String id) {
        return new Response(true, StatusCode.OK, "查询成功", adminUserService.findById(id));
    }

    /*
     * 分页+多条件查询
     * @param searchMap 查询条件封装
     * @param page 页码
     * @param size 页大小
     * @return 分页结果
     */
    @ApiOperation(value = "动态条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchMap", value = "查询条件", required = true, dataType = "AdminUser", dataTypeClass = AdminUser.class),
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "int", paramType = "path")
    })
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Response pageSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<AdminUser> pageList = adminUserService.pageSearch(searchMap, page, size);
        return new Response(true, StatusCode.OK, "查询成功", new PageResponse<AdminUser>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @ApiOperation(value = "根据条件查询管理员数据")
    @ApiImplicitParam(name = "searchMap", value = "查询条件", required = true, dataType = "AdminUser", dataTypeClass = AdminUser.class)
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response findSearch(@RequestBody Map searchMap) {
        return new Response(true, StatusCode.OK, "查询成功", adminUserService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param admin
     */
    @ApiOperation(value = "增加管理员")
    @RequestMapping(method = RequestMethod.POST)
    public Response add(@RequestBody AdminUser admin) {
        adminUserService.add(admin);
        return new Response(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param admin
     */
    @ApiOperation(value = "修改管理员")
    @ApiImplicitParam(name = "id", value = "管理员id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response update(@RequestBody AdminUser admin, @PathVariable String id) {
        admin.setId(id);
        adminUserService.update(admin);
        return new Response(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @ApiOperation(value = "删除管理员")
    @ApiImplicitParam(name = "id", value = "管理员id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable String id) {
        adminUserService.deleteById(id);
        return new Response(true, StatusCode.OK, "删除成功");
    }


}
