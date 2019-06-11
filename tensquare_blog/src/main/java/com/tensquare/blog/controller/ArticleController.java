package com.tensquare.blog.controller;

import com.tensquare.blog.pojo.Article;
import com.tensquare.blog.service.ArticleService;
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
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/article")
@Api(tags = "文章Controller")
public class ArticleController {

	@Autowired
	private ArticleService articleService;


	/*
	*  TODO 点赞 可以用 redis控制不能重复点赞， 评论和点赞都是通过mq进行处理，快速返回结果给用户
	* */
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@ApiOperation(value = "查询全部数据", notes = "查询全部article")
	@RequestMapping(method= RequestMethod.GET)
	public Response findAll(){
		return new Response(true,StatusCode.OK,"查询成功",articleService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@ApiOperation(value = "通过id获取article信息", notes = "通过id获取article信息")
	@ApiImplicitParam(name = "id", value = "需要查询的id",required = true,dataType = "String",paramType = "path")
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Response findById(@PathVariable String id){
		return new Response(true,StatusCode.OK,"查询成功",articleService.findById(id));
	}


	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@ApiOperation(value = "分页条件查询", notes = "分页条件查询")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "searchMap", value = "查询条件", required = true,dataType = "Article", dataTypeClass = Article.class),
			@ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int",paramType = "path"),
			@ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "int",paramType = "path")
	})
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Response findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Article> pageList = articleService.findSearch(searchMap, page, size);
		return  new Response(true,StatusCode.OK,"查询成功",  new PageResponse<Article>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
	@ApiOperation(value = "条件查询", notes = "条件查询")
	@ApiImplicitParam(name = "searchMap", value = "查询条件", required = true, dataType = "Article",dataTypeClass = Article.class)
	@RequestMapping(value="/search",method = RequestMethod.POST)
    public Response findSearch( @RequestBody Map searchMap){
        return new Response(true,StatusCode.OK,"查询成功",articleService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param article
	 */
	@ApiOperation(value = "增加用户", notes = "")
	@RequestMapping(method=RequestMethod.POST)
	public Response add(@RequestBody Article article  ){
		articleService.add(article);
		return new Response(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param article
	 */
	@ApiOperation(value = "修改文章", notes = "")
	@ApiImplicitParam(name = "id", value = "需要修改的id",required = true,dataType = "String",paramType = "path")
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Response update(@RequestBody Article article, @PathVariable String id ){
		article.setId(id);
		articleService.update(article);		
		return new Response(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@ApiOperation(value = "删除文章", notes = "")
	@ApiImplicitParam(name = "id", value = "删除的文章id", required = true,dataType = "String",paramType = "path")
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Response delete(@PathVariable String id ){
		articleService.deleteById(id);
		return new Response(true,StatusCode.OK,"删除成功");
	}
	
}
