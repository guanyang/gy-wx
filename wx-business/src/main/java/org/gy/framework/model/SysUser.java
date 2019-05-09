package org.gy.framework.model;

import java.util.Date;

public class SysUser {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 用户名
     *
     * @mbggenerated
     */
    private String userName;

    /**
     * 账户
     *
     * @mbggenerated
     */
    private String accountName;

    /**
     * 密码
     *
     * @mbggenerated
     */
    private String password;

    /**
     * 加盐值
     *
     * @mbggenerated
     */
    private String salt;

    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column sys_user.DESCRIPTION
     *
     * @mbggenerated
     */
    private String description;

    /**
     * 状态，1启动，0禁用
     *
     * @mbggenerated
     */
    private Integer status;

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
     * 获取  用户名
     *
     * @return USER_NAME 用户名
     *
     * @mbggenerated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置  用户名
     *
     * @param userName 用户名
     *
     * @mbggenerated
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * 获取  账户
     *
     * @return ACCOUNT_NAME 账户
     *
     * @mbggenerated
     */
    public String getAccountName() {
        return accountName;
    }

    /**
     * 设置  账户
     *
     * @param accountName 账户
     *
     * @mbggenerated
     */
    public void setAccountName(String accountName) {
        this.accountName = accountName == null ? null : accountName.trim();
    }

    /**
     * 获取  密码
     *
     * @return PASSWORD 密码
     *
     * @mbggenerated
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置  密码
     *
     * @param password 密码
     *
     * @mbggenerated
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取  加盐值
     *
     * @return SALT 加盐值
     *
     * @mbggenerated
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置  加盐值
     *
     * @param salt 加盐值
     *
     * @mbggenerated
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column sys_user.DESCRIPTION
     *
     * @return the value of sys_user.DESCRIPTION
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column sys_user.DESCRIPTION
     *
     * @param description the value for sys_user.DESCRIPTION
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取  状态，1启动，0禁用
     *
     * @return STATUS 状态，1启动，0禁用
     *
     * @mbggenerated
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置  状态，1启动，0禁用
     *
     * @param status 状态，1启动，0禁用
     *
     * @mbggenerated
     */
    public void setStatus(Integer status) {
        this.status = status;
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