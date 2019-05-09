package org.gy.framework.util.jedis.support;

import org.gy.framework.util.jedis.JedisBean;

import redis.clients.jedis.JedisCommands;

/**
 * 功能描述：
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class ListStrategy implements Strategy {

    @Override
    public Object set(JedisCommands jedis,
                      JedisBean bean) {
        String key = bean.getKey();
        int seconds = bean.getTime();
        String value = bean.getValue().toString();

        Long result = jedis.rpush(key, value);// 执行 RPUSH 操作后，表的长度。
        if (result == 1) {
            jedis.expire(key, seconds);// 避免超时时间被覆盖
        }
        return result;
    }

    @Override
    public Object get(JedisCommands jedis,
                      JedisBean bean) {
        String key = bean.getKey();
        if (bean.getStart() == null || bean.getEnd() == null) {
            return jedis.lrange(key, 0, -1);
        } else {
            return jedis.lrange(key, bean.getStart(), bean.getEnd());
        }
    }

}
