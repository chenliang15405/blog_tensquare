package com.tensquare.demo.redisson;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author alan.chen
 * @date 2020/7/5 6:30 PM
 */
@Controller
public class RedissonController {

    @Autowired
    public RedissonClient redissonClient;
    @Autowired
    public StringRedisTemplate stringRedisTemplate;


    /**
     * Redisson加锁和释放锁
     */
    @RequestMapping("/redisson")
    public void getRedisson() {

        // 只要锁名称相同，则为同一把锁，分布式中可以获取到同一把锁对象
        RLock lock = redissonClient.getLock("redis-lock");

        // 默认加锁时间30s，如果长时间没有释放，则会自动延长锁时间，会阻塞等待
        lock.lock();

        try {
            Thread.sleep(1 * 1000 * 60 * 30);
            System.out.println("获取到锁对象，执行代码");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("释放锁");
            lock.unlock();
        }
    }


    /**
     * Rediss可以实现分布式的读写锁
     */
    @RequestMapping("write")
    public void redissonWriteLock() {
        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");

        RLock writeLock = lock.writeLock();

        writeLock.lock();

        try {
            System.out.println("写锁加锁成功。。。");

            Thread.sleep(20000);
            stringRedisTemplate.opsForValue().set("1", "1");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("写锁释放");
            writeLock.unlock();
        }
    }

    /**
     * Rediss可以实现分布式的读写锁
     *
     * 原理：在Redis中保存标示位作为锁标志位，并且保存mode来判定是读锁还是写锁
     *
     */
    @RequestMapping("read")
    public void redissonReadLock() {
        RReadWriteLock lock = redissonClient.getReadWriteLock("rw-lock");

        RLock readLock = lock.readLock();

        readLock.lock();

        try {
            System.out.println("获取读锁。。。");

            String s = stringRedisTemplate.opsForValue().get("1");

            System.out.println("查询数据: " + s);

            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            System.out.println("写锁释放");
            readLock.unlock();
        }
    }


    @RequestMapping("/addSemaphore")
    public void redissonSemaphore() {
        RSemaphore stock = redissonClient.getSemaphore("stock");
        stock.trySetPermits(100);
    }

    @RequestMapping("/addSemaphore/{id}")
    public void redissonSemaphoreDecr(@PathVariable("id") Integer id) {
        RSemaphore stock = redissonClient.getSemaphore("stock");
        if(id % 2 == 0) {
            boolean b = stock.tryAcquire(5);
            if(b) {
                System.out.println("减库存成功1111" + id + b);
            } else {
                System.out.println("xxxxxxxxxx11111");
            }
        } else {
            boolean b = stock.tryAcquire();
            if(b) {
                System.out.println("减库存成功2222" + id + b);
            } else {
                System.out.println("xxxxxxxxxx");
            }
        }
    }


}
