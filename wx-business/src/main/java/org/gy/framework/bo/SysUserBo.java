/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-business
 * FileName: SysUserBo.java
 *
 * @Date 2016-08-05 22:47:13
 */
package org.gy.framework.bo;

import java.util.Date;

import org.gy.framework.util.generator.Pagination;

/**
 * 功能描述：系统管理用户记录BO
 * 
 * @Date 2016-08-05 22:47:13
 */
public class SysUserBo extends Pagination {
    /**
     * 主键
     */
    private Long    id;
    /**
     * 用户名
     */
    private String  userName;
    /**
     * 账户
     */
    private String  accountName;
    /**
     * 密码
     */
    private String  password;
    /**
     * 加盐值
     */
    private String  salt;
    /**
     * 描述
     */
    private String  description;
    /**
     * 状态，1启动，0禁用
     */
    private Integer status;
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
     * 获取用户名
     * 
     * @return userName 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     * 
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取账户
     * 
     * @return accountName 账户
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置账户
     * 
     * @param accountName 账户
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    /**
     * 获取密码
     * 
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     * 
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取加盐值
     * 
     * @return salt 加盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置加盐值
     * 
     * @param salt 加盐值
     */
    public void setSalt(String salt) {
        this.salt = salt;
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
     * 获取状态，1启动，0禁用
     * 
     * @return status 状态，1启动，0禁用
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态，1启动，0禁用
     * 
     * @param status 状态，1启动，0禁用
     */
    public void setStatus(Integer status) {
        this.status = status;
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
