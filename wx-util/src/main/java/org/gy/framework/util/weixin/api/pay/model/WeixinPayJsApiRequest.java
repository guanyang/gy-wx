package org.gy.framework.util.weixin.api.pay.model;

public class WeixinPayJsApiRequest {

    /**
     * 预支付交易会话标识,用于后续接口调用中使用，该值有效期为2小时
     */
    private String prepayId;

    /**
     * JSAPI票据
     */
    private String jsApiTicket;

    /**
     * 当前网页的URL，不包含#及其后面部分
     */
    private String url;

    /**
     * 交易类型
     */
    private String tradeType;
    /**
     * 微信二维码url
     */
    private String codeUrl;

    public String getPrepayId() {
        return prepayId;
    }

    public void setPrepayId(String prepayId) {
        this.prepayId = prepayId;
    }

    public String getJsApiTicket() {
        return jsApiTicket;
    }

    public void setJsApiTicket(String jsApiTicket) {
        this.jsApiTicket = jsApiTicket;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTradeType() {
        return tradeType;
    }

    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    public String getCodeUrl() {
        return codeUrl;
    }

    public void setCodeUrl(String codeUrl) {
        this.codeUrl = codeUrl;
    }

}
