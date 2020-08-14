package com.tensquare.blog;

import com.github.tobato.fastdfs.FdfsClientConfig;
import com.tensquare.common.utils.IdWorker;
import com.tensquare.common.utils.IpAddressUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Import(FdfsClientConfig.class)
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING) // fastdfs配置，可以直接将注解放到启动类，也可以通过写一个bean
@EnableDiscoveryClient
@EnableFeignClients
@EnableEurekaClient
@EnableSwagger2
@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(BlogApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}

	@Bean
	public IpAddressUtil ipAddressUtil() {
		return new IpAddressUtil();
	}
	
}
