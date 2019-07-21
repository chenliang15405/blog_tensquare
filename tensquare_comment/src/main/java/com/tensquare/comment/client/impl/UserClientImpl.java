package com.tensquare.comment.client.impl;

import com.tensquare.comment.client.UserClient;
import com.tensquare.common.entity.Response;
import com.tensquare.common.entity.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @auther alan.chen
 * @time 2019/7/20 3:26 PM
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
