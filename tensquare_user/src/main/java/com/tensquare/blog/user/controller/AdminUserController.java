package com.tensquare.blog.user.controller;

import com.tensquare.blog.user.entity.AdminUser;
import com.tensquare.blog.user.service.AdminUserService;
import com.tensquare.common.entity.PageResponse;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import com.tensquare.common.utils.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @Autowired
    private JwtUtil jwtUtil;


    /**
     * 后台登录
     * @param adminUser
     * @return
     */
    /*@ApiOperation(value = "后台登录", notes = "")
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Response login(@RequestBody AdminUser adminUser) {
        AdminUser loginAdmin = adminUserService.login(adminUser);
        if(loginAdmin == null) {
            return new Response(true, StatusCode.LONGINERROR, "登录失败");
        }
        //使得前后端可以通话的操作，使用jwt实现
        //TODO 目前没有角色表，所以是固定的admin
        //生成令牌，将token返回给用户，用户请求资源的时候，带上token作为标识，来保证是当前登录的用户，作为认证机制
        String token = jwtUtil.createJWT(loginAdmin.getId(), loginAdmin.getLoginname(), "admin");

        Map<String,String> map = Maps.newHashMap();
        map.put("token", token);
        map.put("role", "role");

        return new Response(true, StatusCode.OK, "登录成功", map);

    }*/

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
    @PreAuthorize("hasRole('ADMIN')") // 还可以使用这个注解表示需要权限
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
