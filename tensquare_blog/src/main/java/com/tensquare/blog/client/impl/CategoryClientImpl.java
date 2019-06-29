package com.tensquare.blog.client.impl;

import com.tensquare.blog.client.CategoryClient;
import com.tensquare.common.entity.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Hystrix中的熔断器方法，如果调用的服务挂掉，则调用此方法
 *
 * @auther alan.chen
 * @time 2019/6/15 11:12 AM
 */
@Slf4j
@Component
public class CategoryClientImpl implements CategoryClient {


    @Override
    public Response findById(String id) {
        log.error("CategoryClient: findById(): hystrix start----");
        throw new RuntimeException("CategoryClient: findByCategoryname(): hystrix start----");
    }

    @Override
    public Response save(String json) {
        log.error("CategoryClient: save(): hystrix start----");
        throw new RuntimeException("CategoryClient: findByCategoryname(): hystrix start----");
    }

    @Override
    public Response findByCategoryname(String categoryname) {
        log.error("CategoryClient: findByCategoryname(): hystrix start----");
        throw new RuntimeException("CategoryClient: findByCategoryname(): hystrix start----");
    }
}
