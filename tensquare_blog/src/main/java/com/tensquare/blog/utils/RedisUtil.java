package com.tensquare.blog.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 操作Redis工具类
 *
 * @auther alan.chen
 * @time 2019/6/15 2:35 PM
 */
@Slf4j
@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, String.valueOf(value));
            result = true;
        } catch (Exception e) {
            log.error("RedisUtil set error => [{}]", e);
        }
        return result;
    }


    public boolean setWithExpire(String key, String value, Integer time, TimeUnit timeUnit) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().set(key, value, time, timeUnit);
            result = true;
        } catch (Exception e) {
            log.error("RedisUtil setWithExpire error => [{}]", e);
        }
        return result;
    }



    /**
     * 更新缓存
     */
    public boolean getAndSet(final String key, String value) {
        boolean result = false;
        try {
            redisTemplate.opsForValue().getAndSet(key, value);
            result = true;
        } catch (Exception e) {
            log.error("RedisUtil getAndSet error => [{}]", e);
        }
        return result;
    }

    /**
     * 删除缓存
     */
    public boolean delete(final String key) {
        boolean result = false;
        try {
            redisTemplate.delete(key);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public void incrNum(String key) {
        redisTemplate.opsForValue().increment(key, 1);
    }


    /**
     * set结构保存
     * @param key
     * @param value
     * @return
     */
    public void setWithStar(String key, String value) {
        redisTemplate.opsForHash().put(key, value, "1");
    }

    /**
     * 查询是否包含该key中的value
     * @param key
     * @param value
     * @return
     */
    public boolean contains(String key, String value) {
        return redisTemplate.opsForHash().hasKey(key, value);
    }

}
