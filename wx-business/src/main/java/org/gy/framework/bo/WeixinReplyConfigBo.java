/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-business
 * FileName: WeixinReplyConfigBo.java
 *
 * @Date 2016-08-08 00:30:54
 */
package org.gy.framework.bo;

import java.util.Date;

import org.gy.framework.util.generator.Pagination;

/**
 * 功能描述：微信回复配置BO
 * 
 * @Date 2016-08-08 00:30:54
 */
public class WeixinReplyConfigBo extends Pagination {
    /**
     * 主键
     */
    private Long    id;
    /**
     * 关键字，唯一标识
     */
    private String  keywords;
    /**
     * 标题
     */
    private String  title;
    /**
     * 描述
     */
    private String  description;
    /**
     * 回复文本
     */
    private String  replyText;
    /**
     * 回复链接
     */
    private String  replyLink;
    /**
     * 回复图片
     */
    private String  replyImg;
    /**
     * 回复类型,1:文本,2:链接,3:图文
     */
    private Integer replyType;
    /**
     * 是否生效，1是，0否
     */
    private Integer enable;
    /**
     * 创建时间
     */
    private Date    createTime;
    /**
     * 更新时间
     */
    private Date    updateTime;
    /**
     * 创建时间起始值
     * 
     */
    private Date    createTimeStart;
    /**
     * 创建时间结束值
     * 
     */
    private Date    createTimeEnd;
    /**
     * 更新时间起始值
     * 
     */
    private Date    updateTimeStart;
    /**
     * 更新时间结束值
     * 
     */
    private Date    updateTimeEnd;

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
     * 获取关键字，唯一标识
     * 
     * @return keywords 关键字，唯一标识
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * 设置关键字，唯一标识
     * 
     * @param keywords 关键字，唯一标识
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * 获取标题
     * 
     * @return title 标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置标题
     * 
     * @param title 标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取描述
     * 
     * @return description 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置描述
     * 
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取回复文本
     * 
     * @return replyText 回复文本
     */
    public String getReplyText() {
        return replyText;
    }

    /**
     * 设置回复文本
     * 
     * @param replyText 回复文本
     */
    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    /**
     * 获取回复链接
     * 
     * @return replyLink 回复链接
     */
    public String getReplyLink() {
        return replyLink;
    }

    /**
     * 设置回复链接
     * 
     * @param replyLink 回复链接
     */
    public void setReplyLink(String replyLink) {
        this.replyLink = replyLink;
    }

    /**
     * 获取回复图片
     * 
     * @return replyImg 回复图片
     */
    public String getReplyImg() {
        return replyImg;
    }

    /**
     * 设置回复图片
     * 
     * @param replyImg 回复图片
     */
    public void setReplyImg(String replyImg) {
        this.replyImg = replyImg;
    }

    /**
     * 获取回复类型,1:文本,2:链接,3:图文
     * 
     * @return replyType 回复类型,1:文本,2:链接,3:图文
     */
    public Integer getReplyType() {
        return replyType;
    }

    /**
     * 设置回复类型,1:文本,2:链接,3:图文
     * 
     * @param replyType 回复类型,1:文本,2:链接,3:图文
     */
    public void setReplyType(Integer replyType) {
        this.replyType = replyType;
    }

    /**
     * 获取是否生效，1是，0否
     * 
     * @return enable 是否生效，1是，0否
     */
    public Integer getEnable() {
        return enable;
    }

    /**
     * 设置是否生效，1是，0否
     * 
     * @param enable 是否生效，1是，0否
     */
    public void setEnable(Integer enable) {
        this.enable = enable;
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
}
