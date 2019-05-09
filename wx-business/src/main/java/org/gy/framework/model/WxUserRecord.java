package org.gy.framework.model;

import java.util.Date;

public class WxUserRecord {
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
     * 微信用户id
     *
     * @mbggenerated
     */
    private String openId;

    /**
     * 关注状态，1已关注，2取消关注
     *
     * @mbggenerated
     */
    private String subStatus;

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
     * 二维码场景ID
     *
     * @mbggenerated
     */
    private String sceneId;

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
     * 获取  微信用户id
     *
     * @return OPEN_ID 微信用户id
     *
     * @mbggenerated
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置  微信用户id
     *
     * @param openId 微信用户id
     *
     * @mbggenerated
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * 获取  关注状态，1已关注，2取消关注
     *
     * @return SUB_STATUS 关注状态，1已关注，2取消关注
     *
     * @mbggenerated
     */
    public String getSubStatus() {
        return subStatus;
    }

    /**
     * 设置  关注状态，1已关注，2取消关注
     *
     * @param subStatus 关注状态，1已关注，2取消关注
     *
     * @mbggenerated
     */
    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus == null ? null : subStatus.trim();
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

    /**
     * 获取  二维码场景ID
     *
     * @return SCENE_ID 二维码场景ID
     *
     * @mbggenerated
     */
    public String getSceneId() {
        return sceneId;
    }

    /**
     * 设置  二维码场景ID
     *
     * @param sceneId 二维码场景ID
     *
     * @mbggenerated
     */
    public void setSceneId(String sceneId) {
        this.sceneId = sceneId == null ? null : sceneId.trim();
    }
}