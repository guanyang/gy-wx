package org.gy.framework.util.jedis.support;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.jedis.JedisBean;

import redis.clients.jedis.JedisCommands;

/**
 * 功能描述：
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class HashStrategy implements Strategy {

    @Override
    public Object set(JedisCommands jedis,
                      JedisBean bean) {

        String key = bean.getKey();
        int seconds = bean.getTime();
        String value = bean.getValue().toString();
        Long result = jedis.hset(key, bean.getField(), value);
        Long num = jedis.hlen(key);// 返回哈希表 key 中域的数量。
        if (num == 1) {
            jedis.expire(key, seconds);// 避免超时时间被覆盖
        }
        return result;

    }

    @Override
    public Object get(JedisCommands jedis,
                      JedisBean bean) {
        String key = bean.getKey();
        if (StringUtils.isBlank(bean.getField())) {
            return jedis.hgetAll(key);
        } else {
            return jedis.hmget(key, bean.getField().split(","));
        }
    }

}
