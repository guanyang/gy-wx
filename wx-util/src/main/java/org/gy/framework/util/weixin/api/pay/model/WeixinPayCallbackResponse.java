package org.gy.framework.util.weixin.api.pay.model;

import java.io.Serializable;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 功能描述：微信支付回调通知响应参数封装
 * 
 */
@XStreamAlias("xml")
public class WeixinPayCallbackResponse implements Serializable {

    private static final long serialVersionUID = 434422671793127986L;

    /**
     * 返回状态码,SUCCESS/FAIL SUCCESS表示商户接收通知成功并校验成功
     */
    private String            return_code;
    /**
     * 返回信息，如非空，为错误原因
     */
    private String            return_msg;

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

}
