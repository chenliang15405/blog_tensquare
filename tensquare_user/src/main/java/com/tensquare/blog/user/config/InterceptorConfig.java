package com.tensquare.blog.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * interceptor的配置，如果需要使用到拦截器，则需要配置这个拦截器
 *    注册拦截器
 *
 * @auther alan.chen
 * @time 2019/6/7 6:39 PM
 */
@Configuration
public class InterceptorConfig extends WebMvcConfigurationSupport {


    // 目前使用了 spring security 不需要该拦截器
//    @Autowired
//    private JwtInterceptor jwtInterceptor;
//
//    /**
//     * 注册拦截器
//     * @param registry
//     */
//    public  void addInterceptors(InterceptorRegistry registry) {
//        //声明需要配置的拦截器对象和拦截的请求
//        registry.addInterceptor(jwtInterceptor)
//                .addPathPatterns("/**")
//                .excludePathPatterns("/**/login/**");
//    }



}
