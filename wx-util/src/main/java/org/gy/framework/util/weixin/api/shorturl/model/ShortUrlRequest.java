package org.gy.framework.util.weixin.api.shorturl.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class ShortUrlRequest {

    /**
     * 类型，此处填long2short，代表长链接转短链接
     */
    @JsonProperty(value = "action")
    private String action = "long2short";

    /**
     * 长链接
     */
    @JsonProperty(value = "long_url")
    private String longUrl;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getLongUrl() {
        return longUrl;
    }

    public void setLongUrl(String longUrl) {
        this.longUrl = longUrl;
    }

}
