package org.gy.framework.util.weixin.util;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 功能描述：微信返回值封装对象
 * 
 */
public class GeneralResponse {

    /** 成功状态码 */
    public static final String SUCCESS_CODE = "0";

    /**
     * 返回码，0表示成功，其他参见微信全局返回码说明
     */
    @JsonProperty(value = "errcode")
    private String             errcode      = "0";

    /**
     * 返回消息
     */
    @JsonProperty(value = "errmsg")
    private String             errmsg       = "ok";

    /**
     * 是否成功判断
     */
    public boolean isSuccess() {
        return SUCCESS_CODE.equals(this.errcode);
    }

    /**
     * 获取返回码，0表示成功，其他参见微信全局返回码说明
     * 
     * @return errcode 返回码，0表示成功，其他参见微信全局返回码说明
     */
    public String getErrcode() {
        return errcode;
    }

    /**
     * 设置返回码，0表示成功，其他参见微信全局返回码说明
     * 
     * @param errcode 返回码，0表示成功，其他参见微信全局返回码说明
     */
    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    /**
     * 获取返回消息
     * 
     * @return errmsg 返回消息
     */
    public String getErrmsg() {
        return errmsg;
    }

    /**
     * 设置返回消息
     * 
     * @param errmsg 返回消息
     */
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

}
