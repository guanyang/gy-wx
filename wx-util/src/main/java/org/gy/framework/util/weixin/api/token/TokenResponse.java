package org.gy.framework.util.weixin.api.token;

import org.codehaus.jackson.annotate.JsonProperty;

import org.gy.framework.util.weixin.util.GeneralResponse;

/**
 * 功能描述：获取token响应对象
 * 
 */
public class TokenResponse extends GeneralResponse {

    /**
     * 获取到的凭证
     */
    @JsonProperty(value = "access_token")
    private String  asscessToken;

    /**
     * 凭证有效时间，单位：秒
     */
    @JsonProperty(value = "expires_in")
    private Integer expireTime;

    /**
     * 获取获取到的凭证
     * 
     * @return asscessToken 获取到的凭证
     */
    public String getAsscessToken() {
        return asscessToken;
    }

    /**
     * 设置获取到的凭证
     * 
     * @param asscessToken 获取到的凭证
     */
    public void setAsscessToken(String asscessToken) {
        this.asscessToken = asscessToken;
    }

    /**
     * 获取凭证有效时间，单位：秒
     * 
     * @return expireTime 凭证有效时间，单位：秒
     */
    public Integer getExpireTime() {
        return expireTime;
    }

    /**
     * 设置凭证有效时间，单位：秒
     * 
     * @param expireTime 凭证有效时间，单位：秒
     */
    public void setExpireTime(Integer expireTime) {
        this.expireTime = expireTime;
    }

}
