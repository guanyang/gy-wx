package org.gy.framework.util.jedis.support;

import org.gy.framework.util.jedis.JedisBean;

import redis.clients.jedis.JedisCommands;

/**
 * 功能描述：
 * 
 * @version 2.0.0
 * @author guanyang
 */
public interface Strategy {

    Object set(JedisCommands jedis,
               JedisBean bean);

    Object get(JedisCommands jedis,
               JedisBean bean);

}
