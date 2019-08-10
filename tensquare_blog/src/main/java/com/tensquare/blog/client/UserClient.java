package com.tensquare.blog.client;

import com.tensquare.blog.client.impl.UserClientImpl;
import com.tensquare.common.entity.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * user 客户端
 *
 * @auther alan.chen
 * @time 2019/6/29 10:10 AM
 */
@FeignClient(value = "tensquare-user",fallback = UserClientImpl.class)
public interface UserClient {

	/**
	 * 通过id获取user
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/user/admin",method = RequestMethod.GET)
	public Response getUserById(@PathVariable("id") String id);
}
