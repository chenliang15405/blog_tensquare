package com.tensquare.blog.controller;

import com.alibaba.fastjson.JSON;
import com.tensquare.blog.client.CategoryClient;
import com.tensquare.blog.pojo.Article;
import com.tensquare.blog.service.ArticleService;
import com.tensquare.blog.utils.RedisUtil;
import com.tensquare.common.entity.PageResponse;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import com.tensquare.common.vo.CategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/article")
@Api(tags = "文章Controller")
public class ArticleController {

    @Autowired
    private ArticleService articleService;
    @Autowired
    private CategoryClient categoryClient;

    @Autowired
    private RedisUtil redisUtil;


    /**
     * 文章点赞，通过返回flag控制文章是否已经点赞过，页面中判断
     * @param articleId
     * @return
     */
    @ApiOperation(value = "文章点赞", notes = "")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/thumbup/{articleId}", method = RequestMethod.PUT)
    public Response thumbup(@PathVariable String articleId) {
        articleService.thumbup(articleId);
        return new Response(true, StatusCode.OK, "点赞成功","done_thumbup");
    }



    /**
     * 查询全部数据
     *
     * @return
     */
    @ApiOperation(value = "查询全部数据", notes = "查询全部article")
    @RequestMapping(method = RequestMethod.GET)
    public Response findAll() {
        List<Article> list = articleService.findAll();
        String categoryName = "";
        for (Article article : list) {
            // 获取到每篇文章的category name
            categoryName = getCategoryNameWithFeign(article.getCategoryid());
            article.setCategoryName(categoryName);
        }
        return new Response(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @ApiOperation(value = "通过id获取article信息", notes = "通过id获取article信息")
    @ApiImplicitParam(name = "id", value = "需要查询的id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Response findById(@PathVariable String id) {
        Article article = articleService.findById(id);
        //获取分类名称
        if(article != null) {
            String categoryName = getCategoryNameWithFeign(article.getCategoryid());
            article.setCategoryName(categoryName);
        }
        return new Response(true, StatusCode.OK, "查询成功", article);
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @ApiOperation(value = "分页条件查询", notes = "分页条件查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "searchMap", value = "查询条件", required = true, dataType = "Article", dataTypeClass = Article.class),
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "int", paramType = "path")
    })
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Response findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Article> pageList = articleService.findSearch(searchMap, page, size);
        List<Article> list = pageList.getContent();
        String categoryName = "";
        for (Article article : list) {
            // 获取到每篇文章的category name
            categoryName = getCategoryNameWithFeign(article.getCategoryid());
            article.setCategoryName(categoryName);
        }

        return new Response(true, StatusCode.OK, "查询成功", new PageResponse<Article>(pageList.getTotalElements(), list));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @ApiOperation(value = "条件查询", notes = "条件查询")
    @ApiImplicitParam(name = "searchMap", value = "查询条件", required = true, dataType = "Article", dataTypeClass = Article.class)
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Response findSearch(@RequestBody Map searchMap) {
        List<Article> list = articleService.findSearch(searchMap);
        String categoryName = "";
        for (Article article : list) {
            // 获取到每篇文章的category name
            categoryName = getCategoryNameWithFeign(article.getCategoryid());
            article.setCategoryName(categoryName);
        }
        return new Response(true, StatusCode.OK, "查询成功", list);
    }

    /**
     * 增加
     *
     * @param article
     */
    @ApiOperation(value = "增加文章", notes = "")
    @RequestMapping(method = RequestMethod.POST)
    public Response add(@RequestBody Article article) {
        articleService.add(article);
        return new Response(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param article
     */
    @ApiOperation(value = "修改文章", notes = "")
    @ApiImplicitParam(name = "id", value = "需要修改的id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Response update(@RequestBody Article article, @PathVariable String id) {
        article.setId(id);
        articleService.update(article);
        return new Response(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @ApiOperation(value = "删除文章", notes = "")
    @ApiImplicitParam(name = "id", value = "删除的文章id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable String id) {
        articleService.deleteById(id);
        return new Response(true, StatusCode.OK, "删除成功");
    }


    /**
     * 根据categoryid，调用feign获取category name
     *
     * 根据redis中查询数据，将分类存储到redis中
     *
     * @param id
     * @return
     */
    public String getCategoryNameWithFeign(String id) {
        if (id == null) {
            return null;
        }
        String name = redisUtil.get("article:category:" + id);
        if(StringUtils.isNotBlank(name)) {
            log.info("根据id: {} ,从缓存中get categoryname : {}",id,name);
            return name;
        }
        Response resp = categoryClient.findById(id);
        Object o = JSON.toJSON(resp.getData());
        CategoryVo categoryVo = JSON.parseObject(String.valueOf(o), CategoryVo.class);
        String categoryname = categoryVo.getCategoryname();
        if(StringUtils.isNotBlank(categoryname)) {
            log.info("将id: {} ,category: {}  存储到redis中：",id,categoryname);
            redisUtil.set("article:category:" + id, categoryname);
        }
        return categoryname;
    }



}
