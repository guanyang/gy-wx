package org.gy.framework.util.auth.token.model;

import org.apache.commons.lang3.RandomStringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

public class TokenConfig {
    /**
     * 唯一标识
     */
    @JsonProperty(value = "u")
    private String uid;
    /**
     * 手机号
     */
    @JsonProperty(value = "m")
    private String mobile;
    /**
     * token过期时间戳
     */
    @JsonProperty(value = "e")
    private long   exp;
    /**
     * 随机字符串
     */
    @JsonProperty(value = "n")
    private String nonce;
    /**
     * 时间戳
     */
    @JsonProperty(value = "t")
    private long   time;

    public TokenConfig() {
        this(null, 0, null);
    }

    public TokenConfig(String uid, long exp, String mobile) {
        this.uid = uid;
        this.mobile = mobile;
        this.exp = exp;
        this.nonce = RandomStringUtils.randomAlphanumeric(16);
        this.time = System.currentTimeMillis();
    }

    /**
     * 获取 uid
     * 
     * @return uid uid
     */
    public String getUid() {
        return uid;
    }

    /**
     * 设置 uid
     * 
     * @param uid uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 获取 exp
     * 
     * @return exp exp
     */
    public long getExp() {
        return exp;
    }

    /**
     * 设置 exp
     * 
     * @param exp exp
     */
    public void setExp(long exp) {
        this.exp = exp;
    }

    /**
     * 获取 time
     * 
     * @return time time
     */
    public long getTime() {
        return time;
    }

    /**
     * 设置 time
     * 
     * @param time time
     */
    public void setTime(long time) {
        this.time = time;
    }

    /**
     * 获取 nonce
     * 
     * @return nonce nonce
     */
    public String getNonce() {
        return nonce;
    }

    /**
     * 设置 nonce
     * 
     * @param nonce nonce
     */
    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
