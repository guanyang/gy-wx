package org.gy.framework.util.weixin.message.xml.request;

import org.gy.framework.util.weixin.message.xml.WeiXinBaseMessage;

/**
 * 
 * 功能描述: 微信请求消息
 * 
 */
public class WeiXinRequest extends WeiXinBaseMessage {

    /**
     * 微信appId，冗余字段，内部使用，不作为微信消息传递
     */
    private String appId;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

}
