package org.gy.framework.util.weixin.api.pay.model;

import java.io.Serializable;

/**
 * 功能描述：微信普通红包请求参数封装
 * 
 */
public class WeixinRedPackRequest implements Serializable {

    private static final long serialVersionUID = 8401387904847481666L;

    /***************** 必填参数 *******************/

    /**
     * 随机字符串
     */
    private String            nonce_str;
    /**
     * 签名
     */
    private String            sign;
    /**
     * 商户订单号
     */
    private String            mch_billno;
    /**
     * 商户号
     */
    private String            mch_id;
    /**
     * 公众账号appid
     */
    private String            wxappid;
    /**
     * 商户名称
     */
    private String            send_name;
    /**
     * 用户openid
     */
    private String            re_openid;
    /**
     * 付款金额，单位分
     */
    private Integer           total_amount;
    /**
     * 红包发放总人数
     */
    private Integer           total_num        = 1;
    /**
     * 红包祝福语
     */
    private String            wishing;
    /**
     * Ip地址
     */
    private String            client_ip;
    /**
     * 活动名称
     */
    private String            act_name;
    /**
     * 备注
     */
    private String            remark;

    /***************** 非必填参数 *******************/
    /**
     * 场景id,发放红包使用场景，红包金额大于200时必传<br>
     * PRODUCT_1:商品促销<br>
     * PRODUCT_2:抽奖<br>
     * PRODUCT_3:虚拟物品兑奖 <br>
     * PRODUCT_4:企业内部福利<br>
     * PRODUCT_5:渠道分润<br>
     * PRODUCT_6:保险回馈<br>
     * PRODUCT_7:彩票派奖<br>
     * PRODUCT_8:税务刮奖<br>
     */
    private String            scene_id;

    /**
     * 活动信息
     */
    private String            risk_info;
    /**
     * 资金授权商户号
     */
    private String            consume_mch_id;

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

    public String getMch_billno() {
        return mch_billno;
    }

    public void setMch_billno(String mch_billno) {
        this.mch_billno = mch_billno;
    }

    public String getMch_id() {
        return mch_id;
    }

    public void setMch_id(String mch_id) {
        this.mch_id = mch_id;
    }

    public String getWxappid() {
        return wxappid;
    }

    public void setWxappid(String wxappid) {
        this.wxappid = wxappid;
    }

    public String getSend_name() {
        return send_name;
    }

    public void setSend_name(String send_name) {
        this.send_name = send_name;
    }

    public String getRe_openid() {
        return re_openid;
    }

    public void setRe_openid(String re_openid) {
        this.re_openid = re_openid;
    }

    public Integer getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Integer total_amount) {
        this.total_amount = total_amount;
    }

    public Integer getTotal_num() {
        return total_num;
    }

    public void setTotal_num(Integer total_num) {
        this.total_num = total_num;
    }

    public String getWishing() {
        return wishing;
    }

    public void setWishing(String wishing) {
        this.wishing = wishing;
    }

    public String getClient_ip() {
        return client_ip;
    }

    public void setClient_ip(String client_ip) {
        this.client_ip = client_ip;
    }

    public String getAct_name() {
        return act_name;
    }

    public void setAct_name(String act_name) {
        this.act_name = act_name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getScene_id() {
        return scene_id;
    }

    public void setScene_id(String scene_id) {
        this.scene_id = scene_id;
    }

    public String getRisk_info() {
        return risk_info;
    }

    public void setRisk_info(String risk_info) {
        this.risk_info = risk_info;
    }

    public String getConsume_mch_id() {
        return consume_mch_id;
    }

    public void setConsume_mch_id(String consume_mch_id) {
        this.consume_mch_id = consume_mch_id;
    }

}
