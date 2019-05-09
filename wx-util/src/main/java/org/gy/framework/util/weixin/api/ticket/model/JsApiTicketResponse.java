package org.gy.framework.util.weixin.api.ticket.model;

import org.codehaus.jackson.annotate.JsonProperty;

import org.gy.framework.util.weixin.util.GeneralResponse;

/**
 * 功能描述：获取ticket响应封装
 * 
 */
public class JsApiTicketResponse extends GeneralResponse {

    /**
     * JSAPI票据
     */
    @JsonProperty(value = "ticket")
    private String ticket;
    /**
     * ticket有效期，单位：秒
     */
    @JsonProperty(value = "expires_in")
    private String expireTime;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

}
