package org.gy.framework.util.jedis.support;

import java.util.HashMap;
import java.util.Map;

import org.gy.framework.util.jedis.JedisBean;
import org.gy.framework.util.jedis.JedisType;

/**
 * 功能描述：
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class StrategyFactory {

    private static final Map<String, Strategy> strategyMap;

    private StrategyFactory() {
    }

    static {
        strategyMap = new HashMap<>();
        strategyMap.put(JedisType.STRING.getCode(), new StringStrategy());
        strategyMap.put(JedisType.LIST.getCode(), new ListStrategy());
        strategyMap.put(JedisType.SET.getCode(), new SetStrategy());
        strategyMap.put(JedisType.ZSET.getCode(), new ZsetStrategy());
        strategyMap.put(JedisType.HASH.getCode(), new HashStrategy());
    }

    public static Strategy getStrategy(String type) {
        return strategyMap.get(type);
    }

    public static Strategy getStrategy(JedisBean bean) {
        return getStrategy(bean.getType());
    }

}
