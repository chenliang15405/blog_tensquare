package com.tensquare.blog.client;

import com.tensquare.common.entity.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 标签feign客户端
 *
 * @auther alan.chen
 * @time 2019/7/3 10:39 PM
 */
@FeignClient(value = "tensquare-tag")
public interface LabelClient {

	@RequestMapping(value = "/label/feign",method = RequestMethod.POST)
	public Response handleArticleLabel(String json);

}
