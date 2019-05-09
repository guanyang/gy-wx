package org.gy.framework.util.weixin.api.pay.model;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 功能描述：微信支付回调通知请求参数封装
 * 
 */
@XStreamAlias("xml")
public class WeixinPayCallbackRequest implements Serializable {

    private static final long  serialVersionUID = -7617065157068400836L;

    // 返回状态码,SUCCESS/FAIL,此字段是通信标识，非交易标识，交易是否成功需要查看result_code来判断
    private String             return_code;

    // 返回信息
    private String             return_msg;

    // 以下字段在return_code为SUCCESS的时候有返回

    // 公众账号ID(必填)
    private String             appid;

    // 商户号(必填)
    private String             mch_id;

    // 设备号(非必填)
    private String             device_info;

    // 随机字符串(必填)
    private String             nonce_str;

    // 签名(必填)
    private String             sign;

    // 签名类型
    private String             sign_type;

    // 业务结果 SUCCESS/FAIL(必填)
    private String             result_code;

    // 错误代码
    private String             err_code;

    // 错误代码描述
    private String             err_code_des;

    // 用户标识(必填)
    private String             openid;

    // 是否关注公众账号 Y-关注，N-未关注
    private String             is_subscribe;

    // 交易类型(必填)
    private String             trade_type;

    // 付款银行(必填)
    private String             bank_type;

    // 订单金额(必填)
    private int                total_fee;

    // 应结订单金额
    private int                settlement_total_fee;

    // 货币种类
    private String             fee_type;

    // 现金支付金额(必填)
    private int                cash_fee;

    // 现金支付货币类型
    private String             cash_fee_type;

    // 微信支付订单号
    private String             transaction_id;

    // 商户订单号
    private String             out_trade_no;

    // 支付完成时间,格式为yyyyMMddHHmmss
    private String             time_end;

    // 附加数据，订单中存入的数据原样返回
    private String             attach;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }

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

    public String getDevice_info() {
        return device_info;
    }

    public void setDevice_info(String device_info) {
        this.device_info = device_info;
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

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getResult_code() {
        return result_code;
    }

    public void setResult_code(String result_code) {
        this.result_code = result_code;
    }

    public String getErr_code() {
        return err_code;
    }

    public void setErr_code(String err_code) {
        this.err_code = err_code;
    }

    public String getErr_code_des() {
        return err_code_des;
    }

    public void setErr_code_des(String err_code_des) {
        this.err_code_des = err_code_des;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getIs_subscribe() {
        return is_subscribe;
    }

    public void setIs_subscribe(String is_subscribe) {
        this.is_subscribe = is_subscribe;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }

    public String getBank_type() {
        return bank_type;
    }

    public void setBank_type(String bank_type) {
        this.bank_type = bank_type;
    }

    public int getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(int total_fee) {
        this.total_fee = total_fee;
    }

    public int getSettlement_total_fee() {
        return settlement_total_fee;
    }

    public void setSettlement_total_fee(int settlement_total_fee) {
        this.settlement_total_fee = settlement_total_fee;
    }

    public String getFee_type() {
        return fee_type;
    }

    public void setFee_type(String fee_type) {
        this.fee_type = fee_type;
    }

    public int getCash_fee() {
        return cash_fee;
    }

    public void setCash_fee(int cash_fee) {
        this.cash_fee = cash_fee;
    }

    public String getCash_fee_type() {
        return cash_fee_type;
    }

    public void setCash_fee_type(String cash_fee_type) {
        this.cash_fee_type = cash_fee_type;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public String getTime_end() {
        return time_end;
    }

    public void setTime_end(String time_end) {
        this.time_end = time_end;
    }

    public String getAttach() {
        return attach;
    }

    public void setAttach(String attach) {
        this.attach = attach;
    }

}
