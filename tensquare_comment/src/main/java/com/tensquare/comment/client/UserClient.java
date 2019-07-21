package com.tensquare.comment.client;

import com.tensquare.comment.client.impl.UserClientImpl;
import com.tensquare.common.entity.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 用户微服务客户端
 *
 * @auther alan.chen
 * @time 2019/7/20 3:25 PM
 */
@FeignClient(value = "tensquare-user",fallback = UserClientImpl.class)
public interface UserClient {

	/**
	 * 通过id获取user
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/{id}",method = RequestMethod.GET)
	public Response getUserById(@PathVariable("id") String id);
}
