package org.gy.framework.util.weixin.api.token;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gy.framework.util.weixin.api.MethodType;
import org.gy.framework.util.weixin.config.WeiXinConfig;
import org.gy.framework.util.weixin.util.WeiXinUtil;

public class TokenRefresh {

    private static final Logger logger        = LoggerFactory.getLogger(TokenRefresh.class);

    public static final String  TOKEN_PATTERN = "https://api.weixin.qq.com/cgi-bin/token?grant_type={0}&appid={1}&secret={2}";

    private TokenRefresh() {
    }

    /**
     * 功能描述: 获取公众号accessToken，默认是商家公众号
     * 
     * @return
     */
    public static TokenResponse getAccessToken() {
        return getAccessToken(WeiXinConfig.getAppId(), WeiXinConfig.getSecret());
    }

    /**
     * 功能描述: 获取公众号accessToken
     * 
     * @return
     */
    public static TokenResponse getAccessToken(String appId,
                                               String secret) {
        if (StringUtils.isBlank(appId) || StringUtils.isBlank(secret)) {
            logger.error("获取公众号accessToken参数错误：appId={},appSecret={}", appId, secret);
            return null;
        }
        String url = WeiXinUtil.wrapApiUrl(TOKEN_PATTERN, WeiXinConfig.getGrantType(), appId, secret);
        return WeiXinUtil.execute(url, MethodType.GET, null, TokenResponse.class);
    }

}
