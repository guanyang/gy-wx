/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-business
 * FileName: WeixinUserRecordBo.java
 *
 * @Date 2016-08-08 00:00:57
 */
package org.gy.framework.bo;

import java.util.Date;

import org.gy.framework.util.generator.Pagination;

/**
 * 功能描述：微信用户记录BO
 * 
 * @Date 2016-08-08 00:00:57
 */
public class WeixinUserRecordBo extends Pagination {
    /**
     * 主键
     */
    private Long   id;
    /**
     * 微信用户id
     */
    private String openId;

    /**
     * 关注状态，1已关注，2取消关注
     */
    private String subStatus;
    /**
     * 创建时间
     */
    private Date   createTime;
    /**
     * 更新时间
     */
    private Date   updateTime;
    /**
     * 创建时间起始值
     * 
     */
    private Date   createTimeStart;
    /**
     * 创建时间结束值
     * 
     */
    private Date   createTimeEnd;
    /**
     * 更新时间起始值
     * 
     */
    private Date   updateTimeStart;
    /**
     * 更新时间结束值
     * 
     */
    private Date   updateTimeEnd;

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
     * 获取创建时间
     * 
     * @return createTime 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     * 
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     * 
     * @return updateTime 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     * 
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * 获取创建时间起始值
     * 
     * @return createTimeStart 创建时间起始值
     */
    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    /**
     * 设置创建时间起始值
     * 
     * @param createTimeStart 创建时间起始值
     */
    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    /**
     * 获取创建时间结束值
     * 
     * @return createTimeEnd 创建时间结束值
     */
    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    /**
     * 设置创建时间结束值
     * 
     * @param createTimeEnd 创建时间结束值
     */
    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    /**
     * 获取更新时间起始值
     * 
     * @return updateTimeStart 更新时间起始值
     */
    public Date getUpdateTimeStart() {
        return updateTimeStart;
    }

    /**
     * 设置更新时间起始值
     * 
     * @param updateTimeStart 更新时间起始值
     */
    public void setUpdateTimeStart(Date updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }

    /**
     * 获取更新时间结束值
     * 
     * @return updateTimeEnd 更新时间结束值
     */
    public Date getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    /**
     * 设置更新时间结束值
     * 
     * @param updateTimeEnd 更新时间结束值
     */
    public void setUpdateTimeEnd(Date updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
    }

    public String getSubStatus() {
        return subStatus;
    }

    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
    }
}
