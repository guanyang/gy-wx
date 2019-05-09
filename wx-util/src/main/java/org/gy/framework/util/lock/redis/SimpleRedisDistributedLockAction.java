package org.gy.framework.util.lock.redis;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.gy.framework.util.jedis.ShardedJedisAction;
import org.gy.framework.util.jedis.ShardedJedisClient;
import org.gy.framework.util.lock.DistributedLockAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.ShardedJedis;

public class SimpleRedisDistributedLockAction implements DistributedLockAction {

    private static final Logger      logger              = LoggerFactory.getLogger(SimpleRedisDistributedLockAction.class);

    private static final String      DEFAULT_LOCK_PREFIX = "_SIMPLE_REDIS_LOCK:";

    private static final long        DEFAULT_SLEEP_TIME  = 100;                                                            // 默认重试等待时间，单位：毫秒

    private static final long        DEFAULT_TIME_OUT    = 1000;                                                           // 默认重试超时时间，单位：毫秒

    private static final int         DEFAULT_EXPIRE_TIME = 10;                                                             // 默认失效时间，单位：秒

    /**
     * 是否获得锁
     */
    private boolean                  locked              = false;

    /**
     * redis客户端
     */
    private final ShardedJedisClient shardedJedisClient;

    /**
     * 分布式锁key
     */
    private final String             redisLockKey;

    /**
     * key失效时间
     */
    private final int                keyExpireTime;

    /**
     * 获取锁失败重试超时时间，如果小于零，则直到获取锁为止，如果等于零，则仅调用一次，不重试，如果大于零，则直到超时，单位：毫秒
     */
    private final long               timeoutMillis;

    /**
     * 获取锁失败等待重试时间，如果小于等于零，则取默认值，单位：毫秒
     */
    private final long               sleepTimeMillis;

    /**
     * 锁令牌
     */
    private final String             redisToken;

    public SimpleRedisDistributedLockAction(ShardedJedisClient shardedJedisClient, String redisLockKey) {
        this(shardedJedisClient, redisLockKey, DEFAULT_EXPIRE_TIME);
    }

    public SimpleRedisDistributedLockAction(ShardedJedisClient shardedJedisClient, String redisLockKey, int keyExpireTime) {
        this(shardedJedisClient, redisLockKey, keyExpireTime, DEFAULT_TIME_OUT, DEFAULT_SLEEP_TIME);
    }

    public SimpleRedisDistributedLockAction(ShardedJedisClient shardedJedisClient, String redisLockKey, int keyExpireTime, long timeoutMillis, long sleepTimeMillis) {
        this(shardedJedisClient, redisLockKey, keyExpireTime, timeoutMillis, sleepTimeMillis, UUID.randomUUID().toString());
    }

    public SimpleRedisDistributedLockAction(ShardedJedisClient shardedJedisClient, String redisLockKey, int keyExpireTime, long timeoutMillis, long sleepTimeMillis, String redisToken) {
        this.shardedJedisClient = shardedJedisClient;
        this.redisLockKey = DEFAULT_LOCK_PREFIX + redisLockKey;
        this.keyExpireTime = keyExpireTime;
        this.timeoutMillis = timeoutMillis;
        this.sleepTimeMillis = sleepTimeMillis;
        this.redisToken = redisToken;
    }

    @Override
    public boolean acquire() {
        long nano = System.nanoTime();
        long sleepTime = getSleepTimeMillis();
        if (sleepTime <= 0) {
            sleepTime = DEFAULT_SLEEP_TIME;
        }
        try {
            while (true) {
                boolean flag = redisLock();
                if (flag) {
                    locked = true;
                    return true;
                }
                // 如果等于零，仅调用一次获取锁，获取失败直接返回
                if (timeoutMillis == 0) {
                    return false;
                }
                // 如果大于零，则直到超时
                if (timeoutMillis > 0) {
                    long time = (System.nanoTime() - nano) + TimeUnit.MILLISECONDS.toNanos(sleepTime) - TimeUnit.MILLISECONDS.toNanos(timeoutMillis);
                    // 如果此时耗费时间加上sleepTime已经超时，直接返回失败即可，不需要sleep，可提升效率
                    if (time >= 0) {
                        return false;
                    }
                }
                Thread.sleep(sleepTime);
            }
        } catch (Exception e) {
            logger.error("DistributedLock acquire error.", e);
        }
        return false;
    }

    @Override
    public void release() {
        redisRelease();
    }

    private boolean redisLock() {
        final String redisKey = getRedisLockKey();
        final int expireTime = getKeyExpireTime();
        final String value = getRedisToken();
        String result = shardedJedisClient.execute(new ShardedJedisAction<String>() {
            @Override
            public String doAction(ShardedJedis shardedJedis) {
                // 从 Redis 2.6.12 版本开始， SET 在设置操作成功完成时，才返回 OK
                return shardedJedis.set(redisKey, value, "nx", "px", expireTime);
            }
        });
        if ("OK".equalsIgnoreCase(result)) {
            return true;
        }
        return false;
    }

    private void redisRelease() {
        if (locked) {
            locked = false;
            final String redisKey = getRedisLockKey();
            final String value = getRedisToken();
            shardedJedisClient.execute(new ShardedJedisAction<Long>() {
                @Override
                public Long doAction(ShardedJedis shardedJedis) {
                    // 获取redis中的值，验证是否与之前设置的值相等，如果相等，则删除，避免删除掉其他线程的锁
                    String tmp = shardedJedis.get(redisKey);
                    if (value.equals(tmp)) {
                        return shardedJedis.del(redisKey);
                    }
                    return 0L;
                }
            });
        }
    }

    public boolean isLocked() {
        return locked;
    }

    public ShardedJedisClient getShardedJedisClient() {
        return shardedJedisClient;
    }

    public String getRedisLockKey() {
        return redisLockKey;
    }

    public int getKeyExpireTime() {
        return keyExpireTime;
    }

    public long getTimeoutMillis() {
        return timeoutMillis;
    }

    public long getSleepTimeMillis() {
        return sleepTimeMillis;
    }

    public String getRedisToken() {
        return redisToken;
    }

}
