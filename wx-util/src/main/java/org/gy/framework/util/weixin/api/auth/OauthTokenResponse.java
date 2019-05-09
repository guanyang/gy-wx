package org.gy.framework.util.weixin.api.auth;

import org.codehaus.jackson.annotate.JsonProperty;

import org.gy.framework.util.weixin.util.GeneralResponse;

public class OauthTokenResponse extends GeneralResponse {

    /**
     * 网页授权凭证
     */
    @JsonProperty(value = "access_token")
    private String accessToken;

    /**
     * 有效期
     */
    @JsonProperty(value = "expires_in")
    private int    expiresIn;

    /**
     * 刷新token
     */
    @JsonProperty(value = "refresh_token")
    private String refreshToken;

    /**
     * 用户openId
     */
    @JsonProperty(value = "openid")
    private String openId;

    /**
     * 作用域
     */
    @JsonProperty(value = "scope")
    private String scope;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(int expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

}
