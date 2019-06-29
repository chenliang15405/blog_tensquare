package com.tensquare.blog.listener;

import com.tensquare.blog.dao.ArticleDao;
import com.tensquare.blog.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 博客点赞mq监听器
 *  如果发生异常，mq中的消息不会被消费掉
 *
 * @auther alan.chen
 * @time 2019/6/15 1:38 PM
 */
@Slf4j
@Component
public class BlogThumbupMqListener {

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private RedisUtil redisUtil;

    private static final String REDIS_KEY_THUMB_UP_COUNT = "thumbup_count:";

    /**
     * 根据articleid 进行点赞操作。
     *
     * 自动创建队列，Exchange 与 Queue绑定,指定routinng-key ,生产者指定exchange 与 routing-key 就可以发送到指定的queue
     * @param articleId
     */
    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("blog_thumbup"),
            key = "thumbup",
            value = @Queue("thumbupQueue")
    ))
    public void blogThumbupMqHandler(String articleId) {
        // 更新数据库中的blog 点赞数
        articleDao.addThumbup(articleId);
        // 更新redis中的点赞数
        String count = redisUtil.get(REDIS_KEY_THUMB_UP_COUNT + articleId);
        if(StringUtils.isNotBlank(count)) {
            redisUtil.getAndSet(REDIS_KEY_THUMB_UP_COUNT  + articleId, Integer.parseInt(count) + 1 + "");
        } else {
            redisUtil.set(REDIS_KEY_THUMB_UP_COUNT + articleId, 1);
        }
    }




}