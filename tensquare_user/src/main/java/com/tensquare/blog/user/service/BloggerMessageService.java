package com.tensquare.blog.user.service;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.tensquare.blog.user.client.BlogClient;
import com.tensquare.blog.user.client.CommentClient;
import com.tensquare.blog.user.dao.BloggerMessageDao;
import com.tensquare.blog.user.entity.BloggerMessage;
import com.tensquare.blog.user.entity.User;
import com.tensquare.blog.user.vo.BloggerMessageVo;
import com.tensquare.common.entity.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @auther alan.chen
 * @time 2019/7/23 11:31 PM
 */
@Service
@Transactional
public class BloggerMessageService {

	@Autowired
	private BloggerMessageDao bloggerMessageDao;

	@Autowired
	private BlogClient blogClient;
	@Autowired
	private CommentClient commentClient;
	@Autowired
	private UserService userService;

	private static final Integer NOT_READ = 0;

	/**
	 * 保存通知消息
	 * @param bloggerMessage
	 */
	public void save(BloggerMessage bloggerMessage) {
		bloggerMessage.setStatus(NOT_READ);
		bloggerMessage.setCreateDate(new Date());
		bloggerMessageDao.save(bloggerMessage);
	}

	/**
	 * 根据状态查询通知消息
	 * @param status
	 * @return
	 */
	public List<BloggerMessageVo> findBloggerMessageByStatus(Integer status) {
		List<BloggerMessage> list = bloggerMessageDao.findBloggerMessageByStatus(status);
		List<BloggerMessageVo> data = Lists.newArrayList();

		list.forEach(item -> {
			BloggerMessageVo vo = new BloggerMessageVo();
			vo.setType(item.getType());
			vo.setStatus(item.getStatus());
			vo.setCreateDate(item.getCreateDate());
			// 查询博客名称
			settingPropsWithRemoteClient(vo, item);
			data.add(vo);
		});

		return data;
	}

	/**
	 * 设置属性
	 * @param vo
	 */
	private void settingPropsWithRemoteClient(BloggerMessageVo vo, BloggerMessage item) {
		// 查询博客标题
		Response response = blogClient.getAricleById(item.getBlogId());
		if(response.getData() != null) {
			Object json = JSON.toJSON(response.getData());
			Map<String,String> map = (Map<String, String>) JSON.parse(String.valueOf(json));
			vo.setBlogTitle(map.get("title"));
		}
		// 查询评论内容
		Response response1 = commentClient.findById(item.getCommentId());
		if(response1.getData() != null) {
			Object json = JSON.toJSON(response1.getData());
			Map<String,String> map = (Map<String, String>) JSON.parse(String.valueOf(json));
			vo.setCommentContent(map.get("content"));
		}
		// 查询用户
		User user = userService.findById(item.getUserId());
		if(user != null) {
			vo.setUserName(user.getNickname());
		}
	}

}
