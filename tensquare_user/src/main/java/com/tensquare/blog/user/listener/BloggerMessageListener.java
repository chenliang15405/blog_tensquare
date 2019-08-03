package com.tensquare.blog.user.listener;

import com.tensquare.blog.user.entity.BloggerMessage;
import com.tensquare.blog.user.service.BloggerMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 博主消息通知监听器
 *
 * @auther alan.chen
 * @time 2019/7/23 11:21 PM
 */
@Slf4j
@Component
@RabbitListener(queuesToDeclare = @Queue("user_comment_message_channel"))
public class BloggerMessageListener {

	@Autowired
	private BloggerMessageService bloggerMessageService;

	@RabbitListener
	public void handleBloggerMessage(Map<String, Object> map) {
		log.info("[user_comment_message_channel]开始处理消息： {}", map);
		// 将消息中数据保存数据库或者redis各一份
		if(map != null) {
			BloggerMessage bloggerMessage = new BloggerMessage();
			bloggerMessage.setUserId((String) map.get("userId"));
			bloggerMessage.setBlogId((String) map.get("blogId"));
			bloggerMessage.setCommentId((Integer) map.get("commentId"));
			bloggerMessageService.save(bloggerMessage);
		}
		log.info("[user_comment_message_channel] 处理消息结束");
	}

}
