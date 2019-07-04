package com.tensquare.tag.controller;

import com.alibaba.fastjson.JSON;
import com.tensquare.common.entity.PageResponse;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import com.tensquare.tag.pojo.Label;
import com.tensquare.tag.service.LabelBlogService;
import com.tensquare.tag.service.TagService;
import com.tensquare.tag.vo.LabelBlogVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
/**
 * 控制器层
 * @author Administrator
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/label")
@Api(tags = "标签Controller")
public class LabelController {

	@Autowired
	private TagService tagService;

	@Autowired
	private LabelBlogService labelBlogService;


	/**
	 * 查询博客和标签关联的数据
	 * @param blogId
	 * @return
	 */
	@ApiOperation(value = "根据博客id查询关联的标签",notes = "")
	@ApiImplicitParam(name = "blogId", value = "需要查询的blogId",required = true,dataType = "String",paramType = "path")
	@RequestMapping(value = "/blog/{blogId}", method = RequestMethod.GET)
	public Response findLabelsByBlogId(@PathVariable String blogId) {
		List<LabelBlogVo> list = labelBlogService.findByBlogId(blogId);
		return new Response(true, StatusCode.OK, "查询成功",list);
	}

	/**
	 * 处理博客和标签的关联
	 * @param
	 * @return
	 */
	@ApiOperation(value = "根据博客id查询关联的标签",notes = "")
	@RequestMapping(value = "/feign", method = RequestMethod.POST)
	public Response handleArticleLabel(@RequestBody String json) {
		// 解析传递的数据
		Map<String,Object> map = (Map<String, Object>) JSON.parse(json);
		String blogId = (String) map.get("blogId");
		List<String> tagList = JSON.parseArray(map.get("tagList").toString(), String.class);
		String flag = labelBlogService.save(blogId, tagList);
		return new Response(true, StatusCode.OK, "保存成功");
	}


	/**
	 * 查询全部数据
	 * @return
	 */
	@ApiOperation(value = "查询全部数据", notes = "查询全部tag")
	@RequestMapping(method= RequestMethod.GET)
	public Response findAll(){
		return new Response(true,StatusCode.OK,"查询成功",tagService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@ApiOperation(value = "通过id获取tag信息", notes = "通过id获取tag信息")
	@ApiImplicitParam(name = "id", value = "需要查询的id",required = true,dataType = "Integer",paramType = "path")
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Response findById(@PathVariable Integer id){
		return new Response(true,StatusCode.OK,"查询成功",tagService.findById(id));
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
			@ApiImplicitParam(name = "searchMap", value = "查询条件", required = true,dataType = "Label", dataTypeClass = Label.class),
			@ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int",paramType = "path"),
			@ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "int",paramType = "path")
	})
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Response findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Label> pageList = tagService.findSearch(searchMap, page, size);
		return  new Response(true,StatusCode.OK,"查询成功",  new PageResponse<Label>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
	@ApiOperation(value = "条件查询", notes = "条件查询")
	@ApiImplicitParam(name = "searchMap", value = "查询条件", required = true, dataType = "Label",dataTypeClass = Label.class)
	@RequestMapping(value="/search",method = RequestMethod.POST)
    public Response findSearch( @RequestBody Map searchMap){
        return new Response(true,StatusCode.OK,"查询成功",tagService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param label
	 */
	@ApiOperation(value = "增加标签", notes = "")
	@RequestMapping(method=RequestMethod.POST)
	public Response add(@RequestBody Label label  ){
		tagService.add(label);
		return new Response(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param label
	 */
	@ApiOperation(value = "修改标签", notes = "")
	@ApiImplicitParam(name = "id", value = "需要修改的id",required = true,dataType = "Integer",paramType = "path")
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Response update(@RequestBody Label label, @PathVariable Integer id ){
		label.setId(id);
		tagService.update(label);
		return new Response(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@ApiOperation(value = "删除分类", notes = "")
	@ApiImplicitParam(name = "id", value = "删除的标签id", required = true,dataType = "Integer",paramType = "path")
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Response delete(@PathVariable Integer id ){
		tagService.deleteById(id);
		return new Response(true,StatusCode.OK,"删除成功");
	}
	
}
