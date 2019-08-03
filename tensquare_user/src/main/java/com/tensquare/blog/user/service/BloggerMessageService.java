package com.tensquare.blog.user.service;

import com.tensquare.blog.user.dao.BloggerMessageDao;
import com.tensquare.blog.user.entity.BloggerMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @auther alan.chen
 * @time 2019/7/23 11:31 PM
 */
@Service
@Transactional
public class BloggerMessageService {

	@Autowired
	private BloggerMessageDao bloggerMessageDao;

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

}
