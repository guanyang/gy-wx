package org.gy.framework.util.weixin.api.pay.model;

public class WeixinPayJsApiResponse {

    // 公众号id
    private String appId;

    // 时间戳
    private String timeStamp;

    // 随机字符串
    private String nonceStr;

    // 订单详情扩展字符串 例如：prepay_id=123456789 统一下单接口返回的prepay_id参数值，提交格式如：prepay_id=***
    private String packageBody;

    // 微信签名方式
    private String signType;

    // 微信签名
    private String paySign;

    // 生成JS-SDK权限验证的签名
    private String signature;

    // 预支付交易会话标识
    private String prepay_id;

    // 交易类型
    private String trade_type;

    // 微信二维码url
    private String code_url;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getPackageBody() {
        return packageBody;
    }

    public void setPackageBody(String packageBody) {
        this.packageBody = packageBody;
    }

    public String getSignType() {
        return signType;
    }

    public void setSignType(String signType) {
        this.signType = signType;
    }

    public String getPaySign() {
        return paySign;
    }

    public void setPaySign(String paySign) {
        this.paySign = paySign;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getPrepay_id() {
        return prepay_id;
    }

    public void setPrepay_id(String prepay_id) {
        this.prepay_id = prepay_id;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getCode_url() {
        return code_url;
    }

    public void setCode_url(String code_url) {
        this.code_url = code_url;
    }

}
