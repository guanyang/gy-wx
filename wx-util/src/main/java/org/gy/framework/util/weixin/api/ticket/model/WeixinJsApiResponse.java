package org.gy.framework.util.weixin.api.ticket.model;

/**
 * 功能描述：页面权限验证配置
 * 
 */
public class WeixinJsApiResponse {

    // 公众号id
    private String appId;
    // 时间戳
    private String timestamp;
    // 随机字符串
    private String nonceStr;
    // 签名
    private String signature;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

}
