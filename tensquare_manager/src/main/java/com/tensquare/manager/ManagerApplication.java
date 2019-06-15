package com.tensquare.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 *  管理端网关服务
 *
 * @auther alan.chen
 * @time 2019/6/15 5:25 PM
 */
@EnableEurekaClient
@EnableZuulProxy
@SpringBootApplication
public class ManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }

}
