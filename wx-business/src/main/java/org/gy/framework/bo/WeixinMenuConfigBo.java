/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-business
 * FileName: WeixinMenuConfigBo.java
 *
 * @Date 2016-08-08 13:01:24
 */
package org.gy.framework.bo;

import java.util.Date;

import org.gy.framework.util.generator.Pagination;

/**
 * 功能描述：微信菜单配置BO
 * 
 * @Date 2016-08-08 13:01:24
 */
public class WeixinMenuConfigBo extends Pagination {
    /**
     * 主键
     */
    private Long    id;
    /**
     * 菜单名称
     */
    private String  menuName;
    /**
     * 菜单类型
     */
    private String  menuType;
    /**
     * 父层ID
     */
    private Long    parentId;
    /**
     * 回复ID
     */
    private Long    replyId;
    /**
     * 排序码
     */
    private Integer sortNo;
    /**
     * 是否生效，1是，0否
     */
    private Integer enable;
    /**
     * 关键字，唯一标识
     */
    private String  keywords;
    /**
     * 标题
     * 
     */
    private String  title;

    /**
     * 描述
     * 
     */
    private String  description;

    /**
     * 回复文本
     * 
     */
    private String  replyText;

    /**
     * 回复链接
     * 
     */
    private String  replyLink;

    /**
     * 回复图片
     * 
     */
    private String  replyImg;
    /**
     * 创建时间
     */
    private Date    createTime;
    /**
     * 修改时间
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
     * 修改时间起始值
     * 
     */
    private Date    updateTimeStart;
    /**
     * 修改时间结束值
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
     * 获取菜单名称
     * 
     * @return menuName 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单名称
     * 
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * 获取菜单类型
     * 
     * @return menuType 菜单类型
     */
    public String getMenuType() {
        return menuType;
    }

    /**
     * 设置菜单类型
     * 
     * @param menuType 菜单类型
     */
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    /**
     * 获取父层ID
     * 
     * @return parentId 父层ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置父层ID
     * 
     * @param parentId 父层ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取回复ID
     * 
     * @return replyId 回复ID
     */
    public Long getReplyId() {
        return replyId;
    }

    /**
     * 设置回复ID
     * 
     * @param replyId 回复ID
     */
    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    /**
     * 获取排序码
     * 
     * @return sortNo 排序码
     */
    public Integer getSortNo() {
        return sortNo;
    }

    /**
     * 设置排序码
     * 
     * @param sortNo 排序码
     */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
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
     * 获取修改时间
     * 
     * @return updateTime 修改时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置修改时间
     * 
     * @param updateTime 修改时间
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
     * 获取修改时间起始值
     * 
     * @return updateTimeStart 修改时间起始值
     */
    public Date getUpdateTimeStart() {
        return updateTimeStart;
    }

    /**
     * 设置修改时间起始值
     * 
     * @param updateTimeStart 修改时间起始值
     */
    public void setUpdateTimeStart(Date updateTimeStart) {
        this.updateTimeStart = updateTimeStart;
    }

    /**
     * 获取修改时间结束值
     * 
     * @return updateTimeEnd 修改时间结束值
     */
    public Date getUpdateTimeEnd() {
        return updateTimeEnd;
    }

    /**
     * 设置修改时间结束值
     * 
     * @param updateTimeEnd 修改时间结束值
     */
    public void setUpdateTimeEnd(Date updateTimeEnd) {
        this.updateTimeEnd = updateTimeEnd;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReplyText() {
        return replyText;
    }

    public void setReplyText(String replyText) {
        this.replyText = replyText;
    }

    public String getReplyLink() {
        return replyLink;
    }

    public void setReplyLink(String replyLink) {
        this.replyLink = replyLink;
    }

    public String getReplyImg() {
        return replyImg;
    }

    public void setReplyImg(String replyImg) {
        this.replyImg = replyImg;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
