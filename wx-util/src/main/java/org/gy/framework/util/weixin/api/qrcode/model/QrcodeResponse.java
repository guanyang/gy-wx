package org.gy.framework.util.weixin.api.qrcode.model;

import org.codehaus.jackson.annotate.JsonProperty;

import org.gy.framework.util.weixin.util.GeneralResponse;

public class QrcodeResponse extends GeneralResponse {

    /**
     * 获取的二维码ticket
     */
    @JsonProperty(value = "ticket")
    private String  ticket;

    /**
     * 该二维码有效时间，以秒为单位，仅对临时二维码有效。 最大不超过2592000（即30天）。
     */
    @JsonProperty(value = "expire_seconds")
    private Integer expireSeconds;

    /**
     * 二维码图片解析后的地址
     */
    @JsonProperty(value = "url")
    private String  url;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpireSeconds() {
        return expireSeconds;
    }

    public void setExpireSeconds(Integer expireSeconds) {
        this.expireSeconds = expireSeconds;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
