package org.gy.framework.util.weixin.api.user;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gy.framework.util.weixin.api.MethodType;
import org.gy.framework.util.weixin.util.WeiXinUtil;

public class WebUserInfoService {
    
    private static final Logger logger            = LoggerFactory.getLogger(WebUserInfoService.class);

    public static final String  USER_INFO_PATTERN = "https://api.weixin.qq.com/sns/userinfo?access_token={0}&openid={1}&lang=zh_CN";

    private WebUserInfoService() {
    }
    
    /**
     * 功能描述:获取用户基本信息
     * 
     * @param accessToken 公众号accessToken
     * @param openId 用户openId
     * @return
     */
    public static UserInfoResponse getWebUserInfo(String accessToken,
                                               String openId) {
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(openId)) {
            logger.error("获取WEB用户基本信息参数错误：accessToken={},openId={}", accessToken, openId);
            return null;
        }
        String url = WeiXinUtil.wrapApiUrl(USER_INFO_PATTERN, accessToken, openId);
        return WeiXinUtil.execute(url, MethodType.GET, null, UserInfoResponse.class);
    }

}
