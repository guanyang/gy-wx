/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-business
 * FileName: ShardedJedisAction.java
 *
 * @Author gy
 * @Date 2016年8月7日下午7:54:48
 */
package org.gy.framework.util.jedis;

import redis.clients.jedis.ShardedJedis;

/**
 * 功能描述：ShardedJedis操作
 *
 * @Author gy
 * @Date 2016年8月7日下午7:54:48
 */
public interface ShardedJedisAction<T> {
    
    public T doAction(ShardedJedis shardedJedis);
}
