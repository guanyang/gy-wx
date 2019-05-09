/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: UserInfoGet.java
 *
 * @Author gy
 * @Date 2016年8月21日上午11:22:22
 */
package org.gy.framework.util.weixin.api.user;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gy.framework.util.json.JacksonMapper;
import org.gy.framework.util.weixin.api.MethodType;
import org.gy.framework.util.weixin.util.WeiXinUtil;

/**
 * 功能描述：获取用户基本信息api
 * 
 * @Author gy
 * @Date 2016年8月21日上午11:22:22
 */
public class UserInfoGet {

    private static final Logger logger            = LoggerFactory.getLogger(UserInfoGet.class);

    public static final String  USER_INFO_PATTERN = "https://api.weixin.qq.com/cgi-bin/user/info?access_token={0}&openid={1}&lang=zh_CN";

    private UserInfoGet() {
    }

    /**
     * 功能描述:获取用户基本信息
     * 
     * @param accessToken 公众号accessToken
     * @param openId 用户openId
     * @return
     */
    public static UserInfoResponse getUserInfo(String accessToken,
                                               String openId) {
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(openId)) {
            logger.error("获取用户基本信息参数错误：accessToken={},openId={}", accessToken, openId);
            return null;
        }
        String url = WeiXinUtil.wrapApiUrl(USER_INFO_PATTERN, accessToken, openId);
        return WeiXinUtil.execute(url, MethodType.GET, null, UserInfoResponse.class);
    }
    
    public static void main(String[] args) {
        String accessToken="xw_KbRbJF1v5a1PDVrVIZpOV8KAIN7N5lxCQwrVjJ2iwzwHfcpc53AjWSEtKt0FHj6-GEqkSMEtaRVrjd39rXScONVw2cuTrmpMw0cwzVtfVk1T1xyVIzjxmPhrRGJ-JVWXfAIADEF";
        String openId="ofUM61pLG8ZKtQy800a9WNlkA1bI";
        UserInfoResponse response = getUserInfo(accessToken, openId);
        System.out.println(JacksonMapper.beanToJson(response));
    }

}
