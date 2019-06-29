package com.tensquare.blog.client.impl;

import com.tensquare.blog.client.UserClient;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * user client的hystrix 处理
 * @auther alan.chen
 * @time 2019/6/29 10:22 AM
 */
@Slf4j
@Component
public class UserClientImpl implements UserClient {

	@Override
	public Response getUserById(String id) {
		log.error("UserClientImpl: getUserById(): [{}] : hystrix method start-------",id);
		return new Response(false, StatusCode.REMOTEERROR, "调用User服务失败");
	}

}
