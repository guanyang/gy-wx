package org.gy.framework.model;

import java.util.Date;

public class WxAppConfig {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 公众号APPID
     *
     * @mbggenerated
     */
    private String appId;

    /**
     * 公众号APPSECRET
     *
     * @mbggenerated
     */
    private String appSecret;

    /**
     * 公众号名称
     *
     * @mbggenerated
     */
    private String appName;

    /**
     * 验证令牌
     *
     * @mbggenerated
     */
    private String validateToken;

    /**
     * 是否启用，1启用，2禁用
     *
     * @mbggenerated
     */
    private Integer enable;

    /**
     * 创建时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 更新时间
     *
     * @mbggenerated
     */
    private Date updateTime;

    /**
     * 获取  主键
     *
     * @return ID 主键
     *
     * @mbggenerated
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置  主键
     *
     * @param id 主键
     *
     * @mbggenerated
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取  公众号APPID
     *
     * @return APP_ID 公众号APPID
     *
     * @mbggenerated
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置  公众号APPID
     *
     * @param appId 公众号APPID
     *
     * @mbggenerated
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
    }

    /**
     * 获取  公众号APPSECRET
     *
     * @return APP_SECRET 公众号APPSECRET
     *
     * @mbggenerated
     */
    public String getAppSecret() {
        return appSecret;
    }

    /**
     * 设置  公众号APPSECRET
     *
     * @param appSecret 公众号APPSECRET
     *
     * @mbggenerated
     */
    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret == null ? null : appSecret.trim();
    }

    /**
     * 获取  公众号名称
     *
     * @return APP_NAME 公众号名称
     *
     * @mbggenerated
     */
    public String getAppName() {
        return appName;
    }

    /**
     * 设置  公众号名称
     *
     * @param appName 公众号名称
     *
     * @mbggenerated
     */
    public void setAppName(String appName) {
        this.appName = appName == null ? null : appName.trim();
    }

    /**
     * 获取  验证令牌
     *
     * @return VALIDATE_TOKEN 验证令牌
     *
     * @mbggenerated
     */
    public String getValidateToken() {
        return validateToken;
    }

    /**
     * 设置  验证令牌
     *
     * @param validateToken 验证令牌
     *
     * @mbggenerated
     */
    public void setValidateToken(String validateToken) {
        this.validateToken = validateToken == null ? null : validateToken.trim();
    }

    /**
     * 获取  是否启用，1启用，2禁用
     *
     * @return ENABLE 是否启用，1启用，2禁用
     *
     * @mbggenerated
     */
    public Integer getEnable() {
        return enable;
    }

    /**
     * 设置  是否启用，1启用，2禁用
     *
     * @param enable 是否启用，1启用，2禁用
     *
     * @mbggenerated
     */
    public void setEnable(Integer enable) {
        this.enable = enable;
    }

    /**
     * 获取  创建时间
     *
     * @return CREATE_TIME 创建时间
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置  创建时间
     *
     * @param createTime 创建时间
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取  更新时间
     *
     * @return UPDATE_TIME 更新时间
     *
     * @mbggenerated
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置  更新时间
     *
     * @param updateTime 更新时间
     *
     * @mbggenerated
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}