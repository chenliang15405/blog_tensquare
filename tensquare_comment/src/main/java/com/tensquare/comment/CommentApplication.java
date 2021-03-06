package com.tensquare.comment;

import com.tensquare.common.utils.IdWorker;
import com.tensquare.common.utils.IpAddressUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 评论微服务
 *
 * @auther alan.chen
 * @time 2019/7/20 12:30 PM
 */
@EnableSwagger2
@EnableDiscoveryClient
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class CommentApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentApplication.class, args);
	}

	@Bean
	public IdWorker idWorker() {
		return new IdWorker(1,1);
	}

	@Bean
	public IpAddressUtil ipAddressUtil() {
		return new IpAddressUtil();
	}
}
