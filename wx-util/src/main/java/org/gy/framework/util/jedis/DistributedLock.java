/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: DistributedLock.java
 *
 * @Author gy
 * @Date 2016年8月14日下午4:29:44
 */
package org.gy.framework.util.jedis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

/**
 * 功能描述：
 * 
 * @Author gy
 * @Date 2016年8月14日下午4:29:44
 */
public class DistributedLock {

    private static final Logger logger             = LoggerFactory.getLogger(DistributedLock.class);

    private static final String LOCK_PREFIX        = "GY:LOCK:";

    private static final long   DEFAULT_SLEEP_TIME = 100;
    /**
     * shardedJedis客户端
     */
    private ShardedJedisClient  shardedJedisClient;
    /**
     * key值
     */
    private String              lockKey;
    /**
     * value值，保证唯一
     */
    private String              value              = UUID.randomUUID().toString();
    /**
     * 有效期，单位：毫秒
     */
    private long                expireTime         = 20000;
    /**
     * 是否获得锁
     */
    private boolean             locked             = false;

    /**
     * 功能描述：构造函数
     * 
     * @param shardedJedisClient shardedJedis客户端
     * @param lockKey key值
     * @param expireTime 有效期，单位：毫秒
     * @Author gy
     * @Date 2016年8月14日下午6:10:49
     */
    public DistributedLock(ShardedJedisClient shardedJedisClient, String lockKey, long expireTime) {
        this.shardedJedisClient = shardedJedisClient;
        this.lockKey = LOCK_PREFIX + lockKey;
        this.expireTime = expireTime;
    }

    /**
     * 功能描述: 获取阻塞锁，如果锁空闲立即返回 ，获取失败 一直重试
     * 
     * @param sleepTime 线程sleep时间，单位：毫秒
     * @throws InterruptedException
     * @Author gy
     * @Date 2016年8月14日下午9:12:40
     */
    public void lock(long sleepTime) throws InterruptedException {
        if (sleepTime <= 0) {
            sleepTime = DEFAULT_SLEEP_TIME;
        }
        do {
            boolean flag = setnxpx();
            if (flag) {
                locked = true;
                return;
            }
            Thread.sleep(sleepTime);
        } while (true);
    }

    /**
     * 功能描述: 获取立即返回锁，如果锁可用，立即返回true， 否则返回false
     * 
     * @return 成功失败标志，true表示获取锁成功，false失败
     * @Author gy
     * @Date 2016年8月14日下午9:06:23
     */
    public boolean tryLock() {
        return tryLock(0, 0);
    }

    /**
     * 功能描述：获取等待重试锁，锁在给定的超时时间内空闲，则获取锁成功 返回true， 否则返回false
     * 
     * @param timeout 重试超时时间，单位：毫秒
     * @param sleepTime 线程sleep时间，仅timeout大于0时有效，单位：毫秒
     * @return 成功失败标志，true表示获取锁成功，false失败
     * @Author gy
     * @Date 2016年8月14日下午9:01:13
     */
    public boolean tryLock(long timeout,
                           long sleepTime) {
        long nano = System.nanoTime();
        try {
            do {
                boolean flag = setnxpx();
                if (flag) {
                    locked = true;
                    return true;
                }
                if (timeout <= 0 || sleepTime <= 0) {
                    return false;
                }
                Thread.sleep(sleepTime);
            } while ((System.nanoTime() - nano) < TimeUnit.MILLISECONDS.toNanos(timeout));
        } catch (Exception e) {
            logger.error("DistributedLock tryLock error:" + e.getMessage(), e);
        }
        return false;
    }

    /**
     * 功能描述: 释放锁
     * 
     * @Author gy
     * @Date 2016年8月14日下午7:51:20
     */
    public void unLock() {
        release();
    }

    private boolean setnxpx() {
        String result = shardedJedisClient.execute(new ShardedJedisAction<String>() {
            @Override
            public String doAction(ShardedJedis shardedJedis) {
                // 从 Redis 2.6.12 版本开始， SET 在设置操作成功完成时，才返回 OK
                return shardedJedis.set(lockKey, value, "nx", "px", expireTime);
            }
        });
        if ("OK".equals(result)) {
            return true;
        }
        return false;
    }

    private void release() {
        if (locked) {
            shardedJedisClient.execute(new ShardedJedisAction<Long>() {
                @Override
                public Long doAction(ShardedJedis shardedJedis) {
                    // 获取redis中的值，验证是否与之前设置的值相等，如果相等，则删除，避免删除掉其他线程的锁
                    String tmp = shardedJedis.get(lockKey);
                    if (value.equals(tmp)) {
                        shardedJedis.del(lockKey);
                    }
                    return null;
                }
            });
            locked = false;
        }
    }

}
