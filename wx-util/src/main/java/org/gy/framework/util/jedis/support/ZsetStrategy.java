package org.gy.framework.util.jedis.support;

import org.gy.framework.util.jedis.JedisBean;

import redis.clients.jedis.JedisCommands;

/**
 * 功能描述：
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class ZsetStrategy implements Strategy {

    @Override
    public Object set(JedisCommands jedis,
                      JedisBean bean) {
        String key = bean.getKey();
        int seconds = bean.getTime();
        String value = bean.getValue().toString();

        Long result = jedis.zadd(key, bean.getScore(), value);// 被成功添加的新成员的数量，不包括那些被更新的、已经存在的成员
        Long num = jedis.zcard(key);// 集合的基数,当 key 不存在时，返回 0 。
        if (num == 1) {
            jedis.expire(key, seconds);// 避免超时时间被覆盖
        }
        return result;
    }

    @Override
    public Object get(JedisCommands jedis,
                      JedisBean bean) {
        String key = bean.getKey();
        if (bean.getStart() == null || bean.getEnd() == null) {
            return jedis.zrange(key, 0, -1);
        } else {
            return jedis.zrange(key, bean.getStart(), bean.getEnd());
        }
    }

}
