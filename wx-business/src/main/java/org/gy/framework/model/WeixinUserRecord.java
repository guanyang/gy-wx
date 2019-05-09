package org.gy.framework.model;

import java.util.Date;

public class WeixinUserRecord {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 微信用户id
     *
     * @mbggenerated
     */
    private String openId;

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