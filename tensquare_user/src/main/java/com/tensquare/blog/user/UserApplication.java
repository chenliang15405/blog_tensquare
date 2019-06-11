package com.tensquare.blog.user;

import com.tensquare.common.utils.IdWorker;
import com.tensquare.common.utils.JwtUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @auther alan.chen
 * @time 2019/6/1 6:07 PM
 */
@EnableEurekaClient
@EnableSwagger2
@SpringBootApplication
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

//    雪花算法生成id
    @Bean
    public IdWorker idWorker() {
        return new IdWorker(1,1);
    }

//    使用spring security提供的加密算法
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    jwt 工具类
    @Bean
    public JwtUtil jwtUtil() {
        return new JwtUtil();
    }

}
