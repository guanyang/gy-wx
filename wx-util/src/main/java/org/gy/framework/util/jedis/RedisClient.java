package org.gy.framework.util.jedis;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisClient implements ShardedJedisClient {

    private static final Logger log = LoggerFactory.getLogger(RedisClient.class);

    private ShardedJedisPool    shardedJedisPool;

    @Override
    public <T> T execute(ShardedJedisAction<T> action) {
        T result = null;

        ShardedJedis shardedJedis = null;
        try {
            shardedJedis = shardedJedisPool.getResource();
            if (shardedJedis != null) {
                result = action.doAction(shardedJedis);
            }
        } catch (Exception e) {
            log.error("RedisClent execute error:" + e.getMessage(), e);
        } finally {
            if (shardedJedis != null) {
                shardedJedis.close();
            }
        }
        return result;

    }

    public String get(final String key) {
        return execute(new ShardedJedisAction<String>() {
            @Override
            public String doAction(ShardedJedis shardedJedis) {
                return shardedJedis.get(key);
            }
        });
    }

    public byte[] get(final byte[] key) {

        return execute(new ShardedJedisAction<byte[]>() {
            @Override
            public byte[] doAction(ShardedJedis shardedJedis) {
                return shardedJedis.get(key);
            }
        });
    }

    public String setex(final byte[] key,
                        final int seconds,
                        final byte[] value) {

        return execute(new ShardedJedisAction<String>() {
            @Override
            public String doAction(ShardedJedis shardedJedis) {
                return shardedJedis.setex(key, seconds, value);
            }
        });
    }

    public String setex(final String key,
                        final int seconds,
                        final String value) {

        return execute(new ShardedJedisAction<String>() {
            @Override
            public String doAction(ShardedJedis shardedJedis) {
                return shardedJedis.setex(key, seconds, value);
            }
        });
    }

    public Long del(final byte[] key) {
        return execute(new ShardedJedisAction<Long>() {
            @Override
            public Long doAction(ShardedJedis shardedJedis) {
                return shardedJedis.del(key);
            }
        });
    }

    public Long del(final String key) {
        return execute(new ShardedJedisAction<Long>() {
            @Override
            public Long doAction(ShardedJedis shardedJedis) {
                return shardedJedis.del(key);
            }
        });
    }

    public Collection<Jedis> getAllShards() {

        return execute(new ShardedJedisAction<Collection<Jedis>>() {
            @Override
            public Collection<Jedis> doAction(ShardedJedis shardedJedis) {
                return shardedJedis.getAllShards();
            }
        });

    }

    public ShardedJedisPool getShardedJedisPool() {
        return shardedJedisPool;
    }

    public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
        this.shardedJedisPool = shardedJedisPool;
    }

}
