package com.tensquare.comment.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tensquare.comment.client.UserClient;
import com.tensquare.comment.config.BusinessException;
import com.tensquare.comment.pojo.Comment;
import com.tensquare.comment.pojo.UserCommentFavorite;
import com.tensquare.comment.service.CommentService;
import com.tensquare.comment.service.UserCommentFavoriteService;
import com.tensquare.common.entity.PageResponse;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import com.tensquare.common.utils.IpAddressUtil;
import com.tensquare.common.vo.CommentRespVo;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论模块Controller
 *
 * @auther alan.chen
 * @time 2019/7/20 12:35 PM
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/comment")
@Api(tags = "评论Controller")
public class CommentController {

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserCommentFavoriteService userCommentFavoriteService;

	@Autowired
	private UserClient userClient;

	@Autowired
	private IpAddressUtil ipAddressUtil;



	/**
	 * 根据博客id查询对应的所有评论(分页查询，滚动无限加载)
	 * @param blogId
	 * @return
	 */
	@ApiOperation(value = "根据评论id分页查询", notes = "")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "blogId", value = "博客id", required = true, dataType = "String",paramType = "path"),
			@ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "int",paramType = "path"),
			@ApiImplicitParam(name = "size", value = "每页条数", required = true, dataType = "int",paramType = "path")
	})
	@RequestMapping(value = "/{blogId}/{page}/{size}", method = RequestMethod.GET)
	public Response getCommentsByBlogId(@PathVariable String blogId, @PathVariable int page, @PathVariable int size, HttpServletRequest request) {
		if(StringUtils.isNotBlank(blogId)) {
			String ip = ipAddressUtil.getIpAddress(request);
			Page<Comment> pageList = commentService.pageByBlogId(blogId, page, size);
			List<CommentRespVo> voList = transferToVo(pageList.getContent(), ip);

			return new Response(true, StatusCode.OK, "查询成功", new PageResponse<CommentRespVo>(pageList.getTotalElements(), voList));
		}
		return new Response(false, StatusCode.ERROR, "blogId为空");
	}

	/**
	 * 根据id查询
	 * @return
	 */
	@ApiOperation(value = "根据评论id查询", notes = "")
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Response findById(@PathVariable Integer id) {
		Comment comment = commentService.findById(id);
		return new Response(true, StatusCode.OK, "查询成功", comment);
	}

	/**
	 * 创建评论
	 * @return
	 */
	@ApiOperation(value = "增加评论", notes = "")
	@RequestMapping(method = RequestMethod.POST)
	public Response createComment(@RequestBody Comment comment) {
		commentService.save(comment);
		return new Response(true, StatusCode.OK, "增加成功");
	}

	/**
	 * 点赞评论
	 * @return
	 */
	@ApiOperation(value = "点赞评论", notes = "")
	@RequestMapping(value = "/favorite", method = RequestMethod.POST)
	public Response favoriteComment(@RequestBody HashMap<String, Object> map, HttpServletRequest request) throws BusinessException {
		String ip = ipAddressUtil.getIpAddress(request);
		commentService.favoriteComment((Integer) map.get("id"), (String)map.get("type"), ip);
		return new Response(true, StatusCode.OK, "点赞成功");
	}

	/**
	 * 删除, 需要带上评论对应的博客文章id
	 * @param id
	 */
	@ApiOperation(value = "删除评论", notes = "")
	@ApiImplicitParam(name = "id", value = "删除评论的id", required = true,dataType = "Integer",paramType = "path")
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Response delete(@PathVariable Integer id ){
		commentService.deleteById(id);
		return new Response(true,StatusCode.OK,"删除成功");
	}

	/**
	 * 回复评论
	 * @param map
	 * @return
	 */
	@ApiOperation(value = "回复评论", notes = "")
	@RequestMapping(value = "/replyComment", method = RequestMethod.POST)
	public Response replyComment(@RequestBody HashMap<String, Object> map) throws BusinessException {
		if(map != null){
			commentService.replyComment((Integer)map.get("commentId"), (String)map.get("replyContent"));
			return new Response(true, StatusCode.OK,"回复成功");
		}
		return new Response(true, StatusCode.OK,"回复失败");
	}


	// 转换实体类
	private List<CommentRespVo> transferToVo(List<Comment> content, String ip) {
//		log.info("调用User微服务查询数据, 转换为VO");
		List<CommentRespVo> list = Lists.newArrayList();
		if(content == null) {
			return null;
		}
		for (Comment comment : content) {
			CommentRespVo vo = new CommentRespVo();
			BeanUtils.copyProperties(comment, vo);
			Map<String, String> map = getCommetnUserInfo(comment.getUserId());
			if(map != null) {
				vo.setAvatar(map.get("avatar"));
				vo.setUserName(map.get("nickname"));
				vo.setSex(map.get("sex"));
			}
			UserCommentFavorite userComment = userCommentFavoriteService.findByCommentIdAndIp(comment.getId(), ip);
			if(userComment != null) {
				vo.setAction(userComment.getAction());
			}

			// 查询children list
			List<Comment> childrenList = commentService.findByParentId(comment.getId());
			// 获取数据
			List<CommentRespVo> childrenVO = transferToVo(childrenList, ip);
			vo.setChildrens(childrenVO);

			list.add(vo);
		}
		return list;
	}


	private Map<String,String> getCommetnUserInfo(String userId) {
		// TODO 需要加缓存
		Response response = userClient.getUserById(userId);
		if(response.getData() != null){
			Object json = JSON.toJSON(response.getData());
			Map<String,String> map = (Map<String, String>) JSON.parse(String.valueOf(json));
			return map;
		}
		return null;
	}


}
