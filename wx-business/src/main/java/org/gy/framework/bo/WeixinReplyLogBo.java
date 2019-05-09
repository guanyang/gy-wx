/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-business
 * FileName: WeixinReplyLogBo.java
 *
 * @Date 2016-08-08 00:12:59
 */
package org.gy.framework.bo;

import java.util.Date;

import org.gy.framework.util.generator.Pagination;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 功能描述：微信回复日志BO
 * 
 * @Date 2016-08-08 00:12:59
 */
public class WeixinReplyLogBo extends Pagination {
    /**
     * 主键
     */
    private Long   id;
    /**
     * 微信用户id
     */
    private String openId;
    /**
     * 类型，包括普通消息、事件消息
     */
    private String type;
    /**
     * 操作时间
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date   createTime;
    /**
     * 内容
     */
    private String content;
    /**
     * 操作时间起始值
     * 
     */
    private Date   createTimeStart;
    /**
     * 操作时间结束值
     * 
     */
    private Date   createTimeEnd;

    /**
     * 获取主键
     * 
     * @return id 主键
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置主键
     * 
     * @param id 主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取微信用户id
     * 
     * @return openId 微信用户id
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置微信用户id
     * 
     * @param openId 微信用户id
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * 获取类型，包括普通消息、事件消息
     * 
     * @return type 类型，包括普通消息、事件消息
     */
    public String getType() {
        return type;
    }

    /**
     * 设置类型，包括普通消息、事件消息
     * 
     * @param type 类型，包括普通消息、事件消息
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取操作时间
     * 
     * @return createTime 操作时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置操作时间
     * 
     * @param createTime 操作时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取内容
     * 
     * @return content 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置内容
     * 
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 获取操作时间起始值
     * 
     * @return createTimeStart 操作时间起始值
     */
    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    /**
     * 设置操作时间起始值
     * 
     * @param createTimeStart 操作时间起始值
     */
    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    /**
     * 获取操作时间结束值
     * 
     * @return createTimeEnd 操作时间结束值
     */
    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    /**
     * 设置操作时间结束值
     * 
     * @param createTimeEnd 操作时间结束值
     */
    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
}
