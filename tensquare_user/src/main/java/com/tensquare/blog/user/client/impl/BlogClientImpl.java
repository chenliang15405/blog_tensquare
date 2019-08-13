package com.tensquare.blog.user.client.impl;

import com.tensquare.blog.user.client.BlogClient;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class BlogClientImpl implements BlogClient {

	@Override
	public Response getAricleById(String id) {
		log.error("BlogClientImpl: getAricleById(): [{}] : hystrix method start-------",id);
		return new Response(false, StatusCode.REMOTEERROR, "调用tensquare-blog服务失败");
	}

}
