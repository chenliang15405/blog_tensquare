package com.tensquare.category.controller;

import com.tensquare.category.pojo.Category;
import com.tensquare.category.service.CategoryService;
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
@RequestMapping("/category")
@Api(tags = "分类Controller")
public class CategoryController {

	@Autowired
	private CategoryService categoryService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@ApiOperation(value = "查询全部数据", notes = "查询全部category")
	@RequestMapping(method= RequestMethod.GET)
	public Response findAll(){
		return new Response(true,StatusCode.OK,"查询成功",categoryService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@ApiOperation(value = "通过id获取category信息", notes = "通过id获取category信息")
	@ApiImplicitParam(name = "id", value = "需要查询的id",required = true,dataType = "Integer",paramType = "path")
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Response findById(@PathVariable String id){
		return new Response(true,StatusCode.OK,"查询成功",categoryService.findById(id));
	}

	/**
	 * 通过名称查询
	 * @param categoryname
	 * @return
	 */
	@ApiOperation(value = "通过id获取category信息", notes = "通过categoryname获取category信息")
	@RequestMapping(value = "/feign/{categoryname}",method = RequestMethod.GET)
	public Response findByCategoryname(@PathVariable String categoryname) {
		return new Response(true,StatusCode.OK,"查询成功",categoryService.findByCategoryname(categoryname));
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
			@ApiImplicitParam(name = "searchMap", value = "查询条件", required = true,dataType = "Category", dataTypeClass = Category.class),
			@ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int",paramType = "path"),
			@ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "int",paramType = "path")
	})
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Response findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Category> pageList = categoryService.findSearch(searchMap, page, size);
		return  new Response(true,StatusCode.OK,"查询成功",  new PageResponse<Category>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
	@ApiOperation(value = "条件查询", notes = "条件查询")
	@ApiImplicitParam(name = "searchMap", value = "查询条件", required = true, dataType = "Category",dataTypeClass = Category.class)
	@RequestMapping(value="/search",method = RequestMethod.POST)
    public Response findSearch( @RequestBody Map searchMap){
        return new Response(true,StatusCode.OK,"查询成功",categoryService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param category
	 */
	@ApiOperation(value = "增加分类", notes = "")
	@RequestMapping(method=RequestMethod.POST)
	public Response add(@RequestBody Category category  ){
		categoryService.add(category);
		return new Response(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param category
	 */
	@ApiOperation(value = "修改分类", notes = "")
	@ApiImplicitParam(name = "id", value = "需要修改的id",required = true,dataType = "Integer",paramType = "path")
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Response update(@RequestBody Category category, @PathVariable String id ){
		category.setId(id);
		categoryService.update(category);		
		return new Response(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@ApiOperation(value = "删除分类", notes = "")
	@ApiImplicitParam(name = "id", value = "删除的分类id", required = true,dataType = "Integer",paramType = "path")
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Response delete(@PathVariable String id ){
		categoryService.deleteById(id);
		return new Response(true,StatusCode.OK,"删除成功");
	}
	
}
