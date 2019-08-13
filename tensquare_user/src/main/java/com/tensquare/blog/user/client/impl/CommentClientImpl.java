package com.tensquare.blog.user.client.impl;

import com.tensquare.blog.user.client.CommentClient;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @auther alan.chen
 * @time 2019/8/13 10:49 PM
 */
@Slf4j
@Component
public class CommentClientImpl implements CommentClient {


	@Override
	public Response findById(Integer id) {
		log.error("CommentClientImpl: findById(): [{}] : hystrix method start-------",id);
		return new Response(false, StatusCode.REMOTEERROR, "调用tensquare-comment服务失败");
	}
}
