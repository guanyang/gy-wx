package org.gy.framework.util.weixin.api.auth;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gy.framework.util.weixin.api.MethodType;
import org.gy.framework.util.weixin.config.WeiXinConfig;
import org.gy.framework.util.weixin.util.WeiXinUtil;

public class OauthTokenService {

    private static final Logger logger              = LoggerFactory.getLogger(OauthTokenService.class);

    public static final String  OAUTH_TOKEN_PATTERN = "https://api.weixin.qq.com/sns/oauth2/access_token?appid={0}&secret={1}&code={2}&grant_type=authorization_code";

    private OauthTokenService() {

    }

    /**
     * 功能描述: 获取网页授权token(公众号appId授权)
     * 
     * @param code 授权临时code
     * @return
     */
    public static OauthTokenResponse execute(String code) {
        return execute(code, WeiXinConfig.getAppId(), WeiXinConfig.getSecret());
    }

    /**
     * 功能描述: 获取网页授权token
     * 
     * @param code 授权临时code
     * @param appId
     * @param appSecret
     * @return
     */
    public static OauthTokenResponse execute(String code,
                                             String appId,
                                             String appSecret) {
        if (StringUtils.isBlank(code) || StringUtils.isBlank(appId) || StringUtils.isBlank(appSecret)) {
            logger.error("获取网页授权token参数错误：code={},appId={},appSecret={}", code, appId, appSecret);
            return null;
        }
        String url = WeiXinUtil.wrapApiUrl(OAUTH_TOKEN_PATTERN, appId, appSecret, code);
        return WeiXinUtil.execute(url, MethodType.GET, null, OauthTokenResponse.class);
    }

}
