package org.gy.framework.util.jedis.support;

import org.gy.framework.util.jedis.JedisBean;

import redis.clients.jedis.JedisCommands;

/**
 * 功能描述：
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class SetStrategy implements Strategy {

    @Override
    public Object set(JedisCommands jedis,
                      JedisBean bean) {
        String key = bean.getKey();
        int seconds = bean.getTime();
        String value = bean.getValue().toString();

        Long result = jedis.sadd(key, value);// 被添加到集合中的新元素的数量，不包括被忽略的元素。
        Long num = jedis.scard(key);// 集合的基数,当 key 不存在时，返回 0 。
        if (num == 1) {
            jedis.expire(key, seconds);// 避免超时时间被覆盖
        }
        return result;
    }

    @Override
    public Object get(JedisCommands jedis,
                      JedisBean bean) {
        String key = bean.getKey();
        return jedis.smembers(key);
    }

}
