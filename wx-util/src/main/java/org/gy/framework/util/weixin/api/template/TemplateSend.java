/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: TemplateService.java
 *
 * @Author gy
 * @Date 2016年8月21日下午10:24:39
 */
package org.gy.framework.util.weixin.api.template;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gy.framework.util.json.JacksonMapper;
import org.gy.framework.util.weixin.api.MethodType;
import org.gy.framework.util.weixin.message.json.template.Template;
import org.gy.framework.util.weixin.util.WeiXinUtil;

/**
 * 功能描述：模板消息发送api
 * 
 * @Author gy
 * @Date 2016年8月21日下午10:24:39
 */
public class TemplateSend {

    private static final Logger logger                = LoggerFactory.getLogger(TemplateSend.class);

    public static final String  TEMPLATE_SEND_PATTERN = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token={0}";

    private TemplateSend() {
    }

    /**
     * 功能描述:发送模板消息
     * 
     * @param accessToken 公众号accessToken
     * @param template 模板消息对象
     * @return
     */
    public static TemplateResponse sendTemplateMessage(String accessToken,
                                                       Template template) {
        if (StringUtils.isBlank(accessToken) || template == null) {
            logger.error("发送模板消息参数错误：accessToken={},template={}", accessToken, JacksonMapper.beanToJson(template));
            return null;
        }
        String url = WeiXinUtil.wrapApiUrl(TEMPLATE_SEND_PATTERN, accessToken);
        return WeiXinUtil.execute(url, MethodType.POST, JacksonMapper.beanToJson(template), TemplateResponse.class);
    }

}
