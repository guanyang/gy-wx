/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: ShardedJedisClient.java
 *
 * @Author gy
 * @Date 2016年8月14日下午4:38:07
 */
package org.gy.framework.util.jedis;


/**
 * 功能描述：ShardedJedis客户端
 * 
 * @Author gy
 * @Date 2016年8月14日下午4:38:07
 */
public interface ShardedJedisClient {

    public <T> T execute(ShardedJedisAction<T> action);

}
