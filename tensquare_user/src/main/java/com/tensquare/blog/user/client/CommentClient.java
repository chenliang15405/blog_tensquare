package com.tensquare.blog.user.client;

import com.tensquare.blog.user.client.impl.CommentClientImpl;
import com.tensquare.common.entity.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @auther alan.chen
 * @time 2019/8/13 10:16 PM
 */
@FeignClient(value = "tensquare-comment",fallback = CommentClientImpl.class)
public interface CommentClient {

	/**
	 * 根据id 查询article
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/comment/{id}", method = RequestMethod.GET)
	public Response findById(@PathVariable("id") Integer id);

}
