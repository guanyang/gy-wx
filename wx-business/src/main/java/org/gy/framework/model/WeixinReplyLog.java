package org.gy.framework.model;

import java.util.Date;

public class WeixinReplyLog {
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
     * 类型，包括普通消息、事件消息
     *
     * @mbggenerated
     */
    private String type;

    /**
     * 操作时间
     *
     * @mbggenerated
     */
    private Date createTime;

    /**
     * 内容
     *
     * @mbggenerated
     */
    private String content;

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
     * 获取  类型，包括普通消息、事件消息
     *
     * @return TYPE 类型，包括普通消息、事件消息
     *
     * @mbggenerated
     */
    public String getType() {
        return type;
    }

    /**
     * 设置  类型，包括普通消息、事件消息
     *
     * @param type 类型，包括普通消息、事件消息
     *
     * @mbggenerated
     */
    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    /**
     * 获取  操作时间
     *
     * @return CREATE_TIME 操作时间
     *
     * @mbggenerated
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置  操作时间
     *
     * @param createTime 操作时间
     *
     * @mbggenerated
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取  内容
     *
     * @return CONTENT 内容
     *
     * @mbggenerated
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置  内容
     *
     * @param content 内容
     *
     * @mbggenerated
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}