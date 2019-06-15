package com.tensquare.blog;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @auther alan.chen
 * @time 2019/6/12 10:37 PM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class RabbitmqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void testSend() {
        Map<String, String> map = new HashMap<>();
        map.put("categoryName", "1");
        map.put("categoryId", "2");
        rabbitTemplate.convertAndSend("blog_category",map);
    }




}
