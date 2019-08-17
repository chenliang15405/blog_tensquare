package com.tensquare.blog.user.listener;

import com.tensquare.blog.user.entity.BloggerMessage;
import com.tensquare.blog.user.service.BloggerMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @auther alan.chen
 * @time 2019/8/13 11:23 PM
 */
@Slf4j
@Component
public class BloggerMessageListenerArticle {

	@Autowired
	private BloggerMessageService bloggerMessageService;

	private static final String FAVORITE_BLOG = "favorite_article";


	/**
	 * 文章点赞消息处理,通过exchange处理，需要实现发布订阅模式
	 * @param articleId
	 */
	@RabbitListener(bindings = @QueueBinding(
			exchange = @Exchange("blog_thumbup"),
			key = "thumbup",
			value = @Queue("thumbupBloggerMessageQueue")
	))
	public void handlerMessage(String articleId) {
		log.info("【博主消息通知模块】处理博客点赞消息通知 START");
		// 保存消息通知到数据库
		BloggerMessage message = new BloggerMessage();
		message.setBlogId(articleId);
		message.setType(FAVORITE_BLOG);
		bloggerMessageService.save(message);
		log.info("【博主消息通知模块】处理博客点赞消息通知 END");
	}

}
