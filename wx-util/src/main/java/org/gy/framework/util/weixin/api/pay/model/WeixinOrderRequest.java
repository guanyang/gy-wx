package org.gy.framework.util.weixin.api.pay.model;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * 功能描述：微信统一下单请求参数封装
 * 
 */
public class WeixinOrderRequest implements Serializable {

    private static final long serialVersionUID = -6994226639347437581L;

    /***************** 必填参数 *******************/
    /**
     * 公众账号ID,32位，必填
     */
    @NotEmpty(message = "公众号appid不能为空")
    @Size(max = 32, message = "公众号appid不能超过32位")
    private String            appid;
    /**
     * 商户号，32位，必填
     */
    @NotEmpty(message = "商户号不能为空")
    @Size(max = 32, message = "商户号不能超过32位")
    private String            mch_id;
    /**
     * 随机字符串，32位，必填
     */
    @NotEmpty(message = "随机字符串不能为空")
    @Size(max = 32, message = "随机字符串不能超过32位")
    private String            nonce_str;

    /**
     * 商品描述，128位，必填
     */
    @NotEmpty(message = "商品描述不能为空")
    @Size(max = 128, message = "商品描述不能超过128位")
    private String            body;

    /**
     * 订单总金额，单位为分,必填
     */
    @NotNull(message = "订单总金额不能为空")
    @Min(value = 1, message = "订单总金额不能小于1分")
    private Integer           total_fee;

    /**
     * 终端IP，16位，必填，APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     */
    @NotEmpty(message = "终端IP不能为空")
    @Size(max = 16, message = "终端IP不能超过16位")
    private String            spbill_create_ip;

    /**
     * 通知地址，256位，必填，异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
     */
    @NotEmpty(message = "通知地址不能为空")
    @Size(max = 256, message = "通知地址不能超过256位")
    private String            notify_url;

    /**
     * 交易类型,16位，必填,取值如下：JSAPI，NATIVE，APP等
     */
    @NotEmpty(message = "交易类型不能为空")
    @Size(max = 16, message = "交易类型不能超过16位")
    private String            trade_type;

    /***************** 特殊处理参数 *******************/

    /**
     * 商品ID,32位，非必填，trade_type=NATIVE时（即扫码支付），此参数必传。此参数为二维码中包含的商品ID，商户自行定义
     */
    @Size(max = 32, message = "商品ID不能超过32位")
    private String            product_id;

    /**
     * 用户标识,128位，非必填，trade_type=JSAPI时（即公众号支付），此参数必传
     */
    @Size(max = 128, message = "用户标识不能超过128位")
    private String            openid;

    /**
     * 签名，32位，必填，签名是最后计算生成，特殊处理
     */
    @Size(max = 32, message = "签名不能超过32位")
    private String            sign;

    /**
     * 商户订单号,32位，必填，由商户系统生成，特殊处理
     */
    @Size(max = 32, message = "商户订单号不能超过32位")
    private String            out_trade_no;

    /***************** 非必填参数 *******************/

    /**
     * 设备号，32位，非必填,PC网页或公众号内支付可以传"WEB"
     */
    @Size(max = 32, message = "设备号不能超过32位")
    private String            device_info      = "WEB";
    /**
     * 签名类型,32位，非必填，默认MD5
     */
    @Size(max = 32, message = "签名类型不能超过32位")
    private String            sign_type;

    /**
     * 商品详细描述,6000位，非必填
     */
    @Size(max = 6000, message = "商品详细描述不能超过6000位")
    private String            detail;

    /**
     * 附加数据，127位，非必填，在查询API和支付通知中原样返回，可作为自定义参数使用
     */
    @Size(max = 127, message = "附加数据不能超过127位")
    private String            attach;

    /**
     * 标价币种,16位，非必填，默认CNY
     */
    @Size(max = 16, message = "标价币种不能超过16位")
    private String            fee_type;

    /**
     * 订单生成时间，格式为yyyyMMddHHmmss，14位，非必填
     */
    @Size(max = 14, message = "订单生成时间不能超过14位")
    private String            time_start;

    /**
     * 订单失效时间，格式为yyyyMMddHHmmss,14位，非必填
     */
    @Size(max = 14, message = "订单失效时间不能超过14位")
    private String            time_expire;

    /**
     * 订单优惠标记，使用代金券或立减优惠功能时需要的参数,32位，非必填
     */
    @Size(max = 32, message = "订单优惠标记不能超过32位")
    private String            goods_tag;

    /**
     * 指定支付方式,32位，非必填，上传此参数no_credit--可限制用户不能使用信用卡支付
     */
    @Size(max = 32, message = "指定支付方式不能超过32位")
    private String            limit_pay;

    /**
     * 场景信息,256位，非必填，该字段用于上报场景信息，目前支持上报实际门店信息。该字段为JSON对象数据
     */
    @Size(max = 256, message = "场景信息不能超过256位")
    private String            scene_info;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getNonce_str() {
        return nonce_str;
    }

    public void setNonce_str(String nonce_str) {
        this.nonce_str = nonce_str;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Integer getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(Integer total_fee) {
        this.total_fee = total_fee;
    }

    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public String getTime_start() {
        return time_start;
    }

    public void setTime_start(String time_start) {
        this.time_start = time_start;
    }

    public String getTime_expire() {
        return time_expire;
    }

    public void setTime_expire(String time_expire) {
        this.time_expire = time_expire;
    }

    public String getGoods_tag() {
        return goods_tag;
    }

    public void setGoods_tag(String goods_tag) {
        this.goods_tag = goods_tag;
    }

    public String getLimit_pay() {
        return limit_pay;
    }

    public void setLimit_pay(String limit_pay) {
        this.limit_pay = limit_pay;
    }

    public String getScene_info() {
        return scene_info;
    }

    public void setScene_info(String scene_info) {
        this.scene_info = scene_info;
    }

}
