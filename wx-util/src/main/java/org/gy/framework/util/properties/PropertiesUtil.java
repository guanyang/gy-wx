/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: PropertiesUtil.java
 *
 * @Author gy
 * @Date 2016年7月17日下午3:23:57
 */
package org.gy.framework.util.properties;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能描述：读取配置信息
 * 
 * @Author gy
 * @Date 2016年7月17日下午3:23:57
 */
public class PropertiesUtil {

    /** 记录日志的变量 */
    private static final Logger                  logger           = LoggerFactory.getLogger(PropertiesUtil.class);

    public static final String                   DEFAULT_LOCATION = "conf/main-setting.properties";

    private static final Map<String, Properties> cacheMap         = new HashMap<String, Properties>();

    private static Properties                    properties;

    static {
        properties = getProperties(DEFAULT_LOCATION);
    }

    /** 私有构造函数 */
    private PropertiesUtil() {
    }

    /**
     * 功能描述:获取指定key对应的value
     * 
     * @param key
     * @return
     */
    public static String getValue(String key) {
        return properties.getProperty(key);
    }

    public static Properties getProperties(String location) {
        Properties properties = cacheMap.get(location);
        if (properties == null) {
            synchronized (cacheMap) {
                if ((properties = cacheMap.get(location)) == null) {
                    properties = loadProperties(location);
                    cacheMap.put(location, properties);
                }
            }
        }
        return properties;
    }

    private static Properties loadProperties(String location) {
        Properties properties = new Properties();
        InputStream is = null;
        try {
            is = PropertiesUtil.class.getClassLoader().getResourceAsStream(location);
            properties.load(is);
        } catch (IOException e) {
            logger.error("加载配置文件异常：" + e.getMessage(), e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                }
            }
        }
        return properties;
    }

}
