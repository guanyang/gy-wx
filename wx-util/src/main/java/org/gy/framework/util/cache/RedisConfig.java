package org.gy.framework.util.cache;

import java.text.MessageFormat;

/**
 * 功能描述：redis配置类
 * 
 */
public class RedisConfig {

    /**** redis用途：缓存 ******/
    public static final String TYPE_CACHE   = "CACHE";
    /**** redis用途：锁 ******/
    public static final String TYPE_LOCK    = "LOCK";
    /**** redis用途：持久化 ******/
    public static final String TYPE_PERSIST = "PERSIST";

    /**
     * 
     * 功能描述: 获取Redis中的key
     * 
     * @param cacheKey CacheKey or LockKey中的枚举数据
     * @param arrays 需要替换的数据，数组格式
     * @return String Redis中的真实key
     */
    public static String getKey(CacheKey cacheKey,
                                Object... arrays) {
        return MessageFormat.format(cacheKey.getKeyPrefix(), arrays);
    }

    public static String getCacheName(CacheKey cacheKey) {
        return cacheKey.toString();
    }

    /**
     * 
     * 功能描述: 定义缓存key<br>
     * <ul>
     * 
     * <li>key的用途：CACHE缓存，LOCK锁，PERSIST持久化</li>
     * 
     * <li>枚举格式要求：{APPCODE}_{key的用途}_{功能}_KEY</li>
     * 
     * <li>占位符要求：占位符可以定义多个或者不定义，示例：{0}:{1}</li>
     * 
     * <li>key格式要求：{APPCODE}:{key的用途}:{功能}:{占位符}</li>
     * 
     * </ul>
     * 
     */
    public enum CacheKey {

        GY_CACHE_SESSION_KEY("GY:CACHE:SESSION:{0}", 1800, "会话缓存"),

        GY_CACHE_RETRY_LIMIT_KEY("GY:CACHE:RETRY_LIMIT:{0}", 600, "密码重试次数限制缓存"),

        GY_CACHE_MUTIL_CONFIG_KEY("GY:CACHE:CONFIG:{0}:{1}", 86400, "系统配置缓存"),

        GY_CACHE_WX_APP_CONFIG_KEY("GY:CACHE:WX:APP_CONFIG", 864000, "微信通用配置缓存"),

        GY_CACHE_WX_REPLY_CONFIG_KEY("GY:CACHE:WX:REPLY_CONFIG:{0}:{1}", 86400, "微信通用回复配置缓存"),

        GY_CACHE_WX_ACCESS_TOKEN_KEY("GY:CACHE:WX:ACCESS_TOKEN:{0}", 7200, "微信公众号access_token缓存"),

        GY_CACHE_AUTH_KEY("GY:CACHE:AUTH:{0}", 86400, "cookie登录鉴权会话缓存"),

        GY_CACHE_SNAPSHOT_KEY("GY:CACHE:SNAPSHOT:{0}", 1800, "cookie登录鉴权POST参数快照"),

        GY_CACHE_JSAPI_TICKET_KEY("GY:CACHE:JSAPI_TICKET", 7200, "微信ticket缓存"),

        GY_CACHE_SHARE_CODE_KEY("GY:CACHE:SHARE_CODE:{0}", 3600, "微信分享CODE缓存");

        /**
         * key前缀
         */
        private String keyPrefix;

        /**
         * 超时时间,单位：秒
         */
        private int    expireTime;
        /**
         * 描述
         */
        private String description;

        CacheKey(String keyPrefix, int expireTime, String description) {
            this.keyPrefix = keyPrefix;
            this.expireTime = expireTime;
            this.description = description;
        }

        public String getKeyPrefix() {
            return keyPrefix;
        }

        public int getExpireTime() {
            return expireTime;
        }

        /**
         * 获取描述
         * 
         * @return description 描述
         */
        public String getDescription() {
            return description;
        }

    }

}
