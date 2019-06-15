package com.tensquare.blog;

import com.tensquare.blog.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @auther alan.chen
 * @time 2019/6/15 2:36 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {

    @Autowired
    private RedisUtil redisUtil;


    @Test
    public void testRedisSet() {
        redisUtil.set("test:test1:test", "1");
    }

    @Test
    public void testRedisGet() {
        System.out.println(redisUtil.get("test"));
    }

}
