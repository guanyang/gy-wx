package org.gy.framework.util.weixin.api.shorturl.model;

import org.codehaus.jackson.annotate.JsonProperty;

import org.gy.framework.util.weixin.util.GeneralResponse;

public class ShortUrlResponse extends GeneralResponse {

    /**
     * 短连接
     */
    @JsonProperty(value = "short_url")
    private String shortUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;
    }

}
