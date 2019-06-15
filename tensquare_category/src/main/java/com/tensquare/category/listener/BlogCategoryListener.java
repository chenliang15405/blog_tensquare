package com.tensquare.category.listener;

import com.tensquare.category.dao.CategoryDao;
import com.tensquare.category.pojo.Category;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * @auther alan.chen
 * @time 2019/6/12 10:57 PM
 */
@Slf4j
@Component
// @RabbitListener(queues = "blog_category") //指定queue的名称，这个名称暂时是在132.232.104.247:15672中创建的队列名称,username:guest password:guest
@RabbitListener(queuesToDeclare = @Queue("blog_category")) // 可以通过queuesToDeclare 自动创建队列
public class BlogCategoryListener {

    @Autowired
    private CategoryDao categoryDao;

//    @RabbitListener(queuesToDeclare = @Queue("blog_category")) 可以直接加方法上
    @RabbitHandler
    public void blogCategoryHandler(Map<String,String> map) {
        log.info("BlogCategoryListener: queues=[blog_category]: 分类id : {}, 分类名称 : {}", map.get("categoryId"),map.get("categoryName"));
        Category category = new Category();
        category.setState("0");
        category.setId(map.get("categoryId"));
        category.setCategoryname(map.get("categoryName"));
        category.setCount(0L);
        category.setCreatedate(new Date());
        categoryDao.save(category);
        log.info("通过mq保存分类完成");
    }



}
