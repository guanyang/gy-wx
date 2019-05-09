package org.gy.framework.util.weixin.api.custom;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gy.framework.util.json.JacksonMapper;
import org.gy.framework.util.weixin.api.MethodType;
import org.gy.framework.util.weixin.message.json.custom.CustomBaseMessage;
import org.gy.framework.util.weixin.util.GeneralResponse;
import org.gy.framework.util.weixin.util.WeiXinUtil;

public class CustomService {

    private static final Logger logger               = LoggerFactory.getLogger(CustomService.class);

    public static final String  SEND_MESSAGE_PATTERN = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token={0}";

    private CustomService() {
    }

    /**
     * 功能描述: 发送客服消息
     * 
     * @param accessToken 公众号accessToken
     * @param customBaseMessage 客服消息对象
     * @return
     */
    public static GeneralResponse sendCustomMessage(String accessToken,
                                                    CustomBaseMessage customBaseMessage) {
        if (StringUtils.isBlank(accessToken) || customBaseMessage == null) {
            logger.error("发送客服消息参数错误：accessToken={},customBaseMessage={}", accessToken, JacksonMapper.beanToJson(customBaseMessage));
            return null;
        }
        String url = WeiXinUtil.wrapApiUrl(SEND_MESSAGE_PATTERN, accessToken);
        return WeiXinUtil.execute(url, MethodType.POST, JacksonMapper.beanToJson(customBaseMessage), GeneralResponse.class);
    }

}
