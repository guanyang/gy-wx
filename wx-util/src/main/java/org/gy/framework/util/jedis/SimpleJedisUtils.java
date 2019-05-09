package org.gy.framework.util.jedis;

import org.gy.framework.util.jedis.support.Strategy;
import org.gy.framework.util.jedis.support.StrategyFactory;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.ShardedJedis;

/**
 * 功能描述：jedis封装工具类
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class SimpleJedisUtils {
    private SimpleJedisUtils() {

    }

    /**
     * 功能描述: 根据key获取相应封装对象
     * 
     * @param jedis
     * @param bean
     * @return
     * @version 2.0.0
     * @author guanyang
     */
    public static JedisBean getByKey(Jedis jedis,
                                     JedisBean bean) {
        return wrapJedisBean(jedis, bean);
    }

    /**
     * 功能描述: 根据key获取相应封装对象
     * 
     * @param jedis
     * @param bean
     * @return
     * @version 2.0.0
     * @author guanyang
     */
    public static JedisBean getByKey(ShardedJedis jedis,
                                     JedisBean bean) {
        return wrapJedisBean(jedis, bean);
    }

    /**
     * 功能描述: 设置redis数据
     * 
     * @param jedis
     * @param bean
     * @return
     * @version 2.0.0
     * @author guanyang
     */
    public static Object setRedis(ShardedJedis jedis,
                                  JedisBean bean) {
        Strategy strategy = StrategyFactory.getStrategy(bean);
        if (strategy != null) {
            return strategy.set(jedis, bean);
        }
        return null;
    }

    private static JedisBean wrapJedisBean(JedisCommands jedis,
                                           JedisBean bean) {
        Strategy strategy = StrategyFactory.getStrategy(bean);
        if (strategy != null) {
            Object value = strategy.get(jedis, bean);
            bean.setValue(value);
        }
        return bean;
    }
}
