package org.gy.framework.util.jedis.support;

import org.gy.framework.util.jedis.JedisBean;

import redis.clients.jedis.JedisCommands;

/**
 * 功能描述：
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class StringStrategy implements Strategy {

    @Override
    public Object set(JedisCommands jedis,
                      JedisBean bean) {
        String key = bean.getKey();
        int seconds = bean.getTime();
        String value = bean.getValue().toString();
        String result = jedis.set(key, value);
        Long num = jedis.ttl(key);// 当 key 存在但没有设置剩余生存时间时，返回 -1
        if (num == -1) {
            jedis.expire(key, seconds);// 避免超时时间被覆盖
        }
        return result;
    }

    @Override
    public Object get(JedisCommands jedis,
                      JedisBean bean) {
        String key = bean.getKey();
        return jedis.get(key);
    }

}
