package com.tensquare.blog.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tensquare.blog.client.CategoryClient;
import com.tensquare.blog.client.UserClient;
import com.tensquare.blog.pojo.Article;
import com.tensquare.blog.service.ArticleService;
import com.tensquare.blog.utils.RedisUtil;
import com.tensquare.blog.vo.ArticleArchiveRespVo;
import com.tensquare.blog.vo.ArticleReqVo;
import com.tensquare.blog.vo.ArticleRespVo;
import com.tensquare.common.constant.RedisKeyConstant;
import com.tensquare.common.entity.PageResponse;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import com.tensquare.common.utils.IpAddressUtil;
import com.tensquare.common.vo.CategoryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.tensquare.common.constant.RedisKeyConstant.ARCHIVE_USER_INFO_KEY;
import static com.tensquare.common.constant.RedisKeyConstant.ARTICLE_CATEGORY_KEY;

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
    private UserClient userClient;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private IpAddressUtil ipAddressUtil;


    /**
     * 文章点赞，通过返回flag控制文章是否已经点赞过，页面中判断
     * @param articleId
     * @return
     */
    @ApiOperation(value = "文章点赞", notes = "")
    @ApiImplicitParam(name = "articleId", value = "文章id", required = true, dataType = "String", paramType = "path")
    @RequestMapping(value = "/thumbup/{articleId}", method = RequestMethod.GET)
    public Response thumbup(@PathVariable String articleId, HttpServletRequest request) {
        String ipAddress = ipAddressUtil.getIpAddress(request);
        int status = articleService.thumbup(articleId, ipAddress);
        if(status == 0) {
            return new Response(true, StatusCode.REPERROR, "已经点赞过了");
        }
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
    public Response findById(@PathVariable String id, HttpServletRequest request) {
        Article article = articleService.findById(id);
        ArticleRespVo vo = new ArticleRespVo();
        //获取分类名称
        if(article != null) {
            BeanUtils.copyProperties(article, vo);

            // 判断当前IP是否点赞
            String ipAddress = ipAddressUtil.getIpAddress(request);
            boolean isStar = redisUtil.contains(RedisKeyConstant.REDIS_KEY_STAR_ARTICLE_USER_PREFIX + id, ipAddress);
            vo.setStared(isStar);

            // 查询分类
            String categoryName = getCategoryNameWithFeign(article.getCategoryid());
            article.setCategoryName(categoryName);
        }
        return new Response(true, StatusCode.OK, "查询成功", vo);
    }

    /**
     * 博客归档（时间）列表查询
     *
     * @param pageSize
     * @return
     */
    @ApiOperation(value = "归档列表查询", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int", paramType = "path"),
            @ApiImplicitParam(name = "pageSize", value = "每次加载的列表条数", required = true, dataType = "int", paramType = "path")
    })
    @RequestMapping(value = "/archive/{page}/{pageSize}", method = RequestMethod.GET)
    public Response findArticleArchiveList(@PathVariable Integer page, @PathVariable Integer pageSize) {
        Page<Article> list = articleService.findArticleArchiveList(page,pageSize);
        List<ArticleArchiveRespVo> resp = transferToArchiveRespVo(list.getContent());
        return new Response(true, StatusCode.OK, "查询成功",new PageResponse<>(list.getTotalElements(),resp));
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
            @ApiImplicitParam(name = "searchMap", value = "查询条件", required = false, dataType = "Article", dataTypeClass = Article.class),
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
    public Response add(@RequestBody ArticleReqVo article) {
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
    public Response update(@RequestBody ArticleReqVo article, @PathVariable String id) {
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
        if (StringUtils.isBlank(id)) {
            return null;
        }
        String categoryname = redisUtil.get(ARTICLE_CATEGORY_KEY + id);
        if(StringUtils.isNotBlank(categoryname)) {
            log.info("根据id: [{}], 从缓存中get categoryname: [{}]", id, categoryname);
            return categoryname;
        }
        Response resp = categoryClient.findById(id);
        if(resp != null && resp.getData() != null) {
            String json = JSON.toJSONString(resp.getData());
            CategoryVo categoryVo = JSON.parseObject(json, CategoryVo.class);
            categoryname = categoryVo.getCategoryname();
            if(StringUtils.isNotBlank(categoryname)) {
                log.info("将id: {} ,category: {}  存储到redis中：",id,categoryname);
                redisUtil.set(ARTICLE_CATEGORY_KEY + id, categoryname);
            }
        }
        return categoryname;
    }

    /**
     * 封装文章归档对象
     * @return
     */
    private List<ArticleArchiveRespVo> transferToArchiveRespVo(List<Article> list) {
        List<ArticleArchiveRespVo> resp = Lists.newArrayList();
        if(list != null && list.size() > 0) {
            list.forEach(item -> {
                ArticleArchiveRespVo vo = new ArticleArchiveRespVo();
                // copy 属性
                BeanUtils.copyProperties(item, vo);
                // 获取user头像
                String userid = item.getUserid();
                String redisData = redisUtil.get(ARCHIVE_USER_INFO_KEY + userid);
                if(StringUtils.isNotBlank(redisData)) {
                    log.info("从缓存中查询到数据，直接解析: {}",redisData);
                    // 直接解析存储的json字符串
                    Map<String,String> map = (Map<String, String>) JSON.parse(redisData);
                    vo.setAvatar(map.get("avatar"));
                } else {
                    log.info("调用User服务，开始查询文章用户信息: [{}]",userid);
                    Response response = userClient.getUserById(userid);
                    if(response.getData() != null){
                        // 保存到缓存中
                        log.info("保存文章的user信息到缓存中: {}",response.getData());
                        // 将对象等必须转换为json格式再存储到redis中，否则json的格式不对
                        Object o = JSON.toJSON(response.getData());
                        redisUtil.set(ARCHIVE_USER_INFO_KEY + userid, String.valueOf(o));
                        Object json = JSON.toJSON(response.getData());
                        Map<String,String> map = (Map<String, String>) JSON.parse(String.valueOf(json));
                        vo.setAvatar(map.get("avatar"));
                    }
                }
                // 获取文章的分类：
                String categoryName = getCategoryNameWithFeign(item.getCategoryid());
                vo.setCategoryName(categoryName);

                resp.add(vo);
            });
        }
        return resp;
    }


}
