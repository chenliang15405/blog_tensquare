package com.tensquare.blog;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @auther alan.chen
 * @time 2019/6/12 10:40 PM
 */
@Component
@RabbitListener(queues = "blog_category")
public class MqReceive {


    @RabbitHandler
    public void receive(String message) {
        System.out.println("接收到的消息" + message);
    }


}
