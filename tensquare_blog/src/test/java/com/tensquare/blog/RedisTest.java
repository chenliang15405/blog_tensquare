package com.tensquare.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tensquare.blog.pojo.Article;
import com.tensquare.blog.utils.RedisUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.tensquare.common.constant.RedisKeyConstant.REDIS_KEY_STAR_ARTICLE_USER_PREFIX;

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
    public void testRedisSet() throws JsonProcessingException {
        redisUtil.set("test:test1:test", "1");
        Article article = new Article();
        article.setUrl("fjldsjljfl");
        article.setType("111");
        article.setTitle("这是一个title");
        article.setContent("解放军队是解放军队就是老骥伏枥");
        ObjectMapper mapper = new ObjectMapper();
        String s = mapper.writeValueAsString(article);
        System.out.println(s);
        redisUtil.set("test", s);
    }

    @Test
    public void testRedisGet() {
        System.out.println(redisUtil.get("test"));
    }

    @Test
    public void testStar() {
        String articleId = "123";
        String ipAddress = "127.0.0.1";
        redisUtil.setWithStar(REDIS_KEY_STAR_ARTICLE_USER_PREFIX + articleId, ipAddress);
    }

    @Test
    public void testHasKey() {
        String articleId = "123";
        String ipAddress = "127.0.0.1";
        boolean contains = redisUtil.contains(REDIS_KEY_STAR_ARTICLE_USER_PREFIX + articleId, ipAddress);
        boolean contains1 = redisUtil.contains(REDIS_KEY_STAR_ARTICLE_USER_PREFIX + articleId, "222");
        System.out.println(contains);
        System.out.println(contains1);
    }
}
