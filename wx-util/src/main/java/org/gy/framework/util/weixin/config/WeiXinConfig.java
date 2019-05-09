package org.gy.framework.util.weixin.config;

import java.io.Serializable;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;

import org.gy.framework.util.weixin.exception.WeiXinException;
import org.gy.framework.util.weixin.util.WeiXinUtil;

/**
 * 微信配置
 */
public class WeiXinConfig implements Serializable {

    private static final long  serialVersionUID       = 7524964169167796587L;

    /**
     * 默认配置文件路径
     */
    public static final String DEFAULT_LOCATION       = "weixin.properties";

    /**
     * 授权类型默认值，获取access_token填写client_credential
     */
    public static final String DEFAULT_GRANT_TYPE     = "client_credential";

    /**
     * 默认超时时间
     */
    public static final int    DEFAULT_EXPIRE_TIME    = 7200;

    /**************************** 默认标识配置 start **************************************/
    /**
     * 微信appID配置标识
     */
    public static final String CONFIG_APPID_KEY       = "wx.appId";
    /**
     * 微信appsecret配置标识
     */
    public static final String CONFIG_SECRET_KEY      = "wx.secret";
    /**
     * 有效时间配置标识
     */
    public static final String CONFIG_EXPIRE_TIME_KEY = "wx.expireTime";

    /**
     * 授权类型配置标识
     */
    public static final String CONFIG_GRANT_TYPE_KEY  = "wx.grantType";
    /**
     * 微信配置token标识
     */
    public static final String CONFIG_TOKEN_KEY       = "wx.token";

    /**************************** 默认标识配置 end **************************************/

    /**
     * 全量配置
     */
    private static Properties  properties;

    static {
        // 初始化全局配置
        initWeiXinConfig(DEFAULT_LOCATION);
    }

    private WeiXinConfig() {

    }

    /**
     * 功能描述:初始化配置
     * 
     * @param configLocation
     */
    public static void initWeiXinConfig(String configLocation) {
        Properties properties = WeiXinUtil.loadProperties(configLocation);
        WeiXinConfig.setProperties(properties);
    }

    /**
     * 获取全量配置
     * 
     * @return properties 全量配置
     */
    public static Properties getProperties() {
        return properties;
    }

    /**
     * 设置全量配置
     * 
     * @param properties 全量配置
     */
    public static void setProperties(Properties properties) {
        WeiXinConfig.properties = properties;
    }

    /**
     * 功能描述:获取指定key对应的value
     * 
     * @param key
     * @return
     */
    public static String getValue(String key) {
        if (WeiXinConfig.properties == null) {
            throw new WeiXinException("[WeiXinConfig]微信全部配置为空，key=" + key);
        }
        return WeiXinConfig.properties.getProperty(key);
    }

    /**
     * 获取微信appId
     * 
     * @return appId 微信appId
     */
    public static String getAppId() {
        return getValue(CONFIG_APPID_KEY);
    }

    /**
     * 获取微信appSecret
     * 
     * @return secret 微信appSecret
     */
    public static String getSecret() {
        return getValue(CONFIG_SECRET_KEY);
    }

    /**
     * 获取有效时间
     * 
     * @return expireTime 有效时间
     */
    public static Integer getExpireTime() {
        String value = getValue(CONFIG_EXPIRE_TIME_KEY);
        return StringUtils.isNotBlank(value) ? Integer.parseInt(value) : DEFAULT_EXPIRE_TIME;
    }

    /**
     * 获取授权类型
     * 
     * @return grantType 授权类型
     */
    public static String getGrantType() {
        String value = getValue(CONFIG_GRANT_TYPE_KEY);
        return StringUtils.isNotBlank(value) ? value : DEFAULT_GRANT_TYPE;
    }

}
