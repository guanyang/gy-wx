package org.gy.framework.bo;

import java.util.Date;

import org.gy.framework.util.generator.Pagination;

/**
 * 功能描述：微信通用配置BO
 * 
 * @Date 2018-01-13 21:19:15
 */
public class WxAppConfigBo extends Pagination{
    /**
    * 主键
    */
    private Long id;
    
    /**
    * 公众号APPID
    */
    private String appId;
    
    /**
    * 公众号APPSECRET
    */
    private String appSecret;
    
    /**
    * 公众号名称
    */
    private String appName;
    
    /**
    * 验证令牌
    */
    private String validateToken;
    
    /**
    * 是否启用，1启用，2禁用
    */
    private Integer enable;
    
    /**
    * 创建时间
    */
    private Date createTime;
    
    /**
    * 创建时间起始值
    */
    private Date createTimeStart;
    /**
    * 创建时间结束值
    */
    private Date createTimeEnd;
    /**
    * 更新时间
    */
    private Date updateTime;
    
    /**
    * 更新时间起始值
    */
    private Date updateTimeStart;
    /**
    * 更新时间结束值
    */
    private Date updateTimeEnd;
    /**
    * 获取主键
    * @return id 主键
    */
    public Long getId() {
    	return id;
    }
    /**
    * 设置主键
    * @param id 主键
    */
    public void setId(Long id) {
    	this.id = id;
    }
    /**
    * 获取公众号APPID
    * @return appId 公众号APPID
    */
    public String getAppId() {
    	return appId;
    }
    /**
    * 设置公众号APPID
    * @param appId 公众号APPID
    */
    public void setAppId(String appId) {
    	this.appId = appId;
    }
    /**
    * 获取公众号APPSECRET
    * @return appSecret 公众号APPSECRET
    */
    public String getAppSecret() {
    	return appSecret;
    }
    /**
    * 设置公众号APPSECRET
    * @param appSecret 公众号APPSECRET
    */
    public void setAppSecret(String appSecret) {
    	this.appSecret = appSecret;
    }
    /**
    * 获取公众号名称
    * @return appName 公众号名称
    */
    public String getAppName() {
    	return appName;
    }
    /**
    * 设置公众号名称
    * @param appName 公众号名称
    */
    public void setAppName(String appName) {
    	this.appName = appName;
    }
    /**
    * 获取验证令牌
    * @return validateToken 验证令牌
    */
    public String getValidateToken() {
    	return validateToken;
    }
    /**
    * 设置验证令牌
    * @param validateToken 验证令牌
    */
    public void setValidateToken(String validateToken) {
    	this.validateToken = validateToken;
    }
    /**
    * 获取是否启用，1启用，2禁用
    * @return enable 是否启用，1启用，2禁用
    */
    public Integer getEnable() {
    	return enable;
    }
    /**
    * 设置是否启用，1启用，2禁用
    * @param enable 是否启用，1启用，2禁用
    */
    public void setEnable(Integer enable) {
    	this.enable = enable;
    }
    /**
    * 获取创建时间
    * @return createTime 创建时间
    */
    public Date getCreateTime() {
    	return createTime;
    }
    /**
    * 设置创建时间
    * @param createTime 创建时间
    */
    public void setCreateTime(Date createTime) {
    	this.createTime = createTime;
    }
    /**
    * 获取创建时间起始值
    * @return createTimeStart 创建时间起始值
    */
    public Date getCreateTimeStart() {
    	return createTimeStart;
    }
    /**
    * 设置创建时间起始值
    * @param createTimeStart 创建时间起始值
    */
    public void setCreateTimeStart(Date createTimeStart) {
    	this.createTimeStart = createTimeStart;
    }
    /**
    * 获取创建时间结束值
    * @return createTimeEnd 创建时间结束值
    */
    public Date getCreateTimeEnd() {
    	return createTimeEnd;
    }
    /**
    * 设置创建时间结束值
    * @param createTimeEnd 创建时间结束值
    */
    public void setCreateTimeEnd(Date createTimeEnd) {
    	this.createTimeEnd = createTimeEnd;
    }
    /**
    * 获取更新时间
    * @return updateTime 更新时间
    */
    public Date getUpdateTime() {
    	return updateTime;
    }
    /**
    * 设置更新时间
    * @param updateTime 更新时间
    */
    public void setUpdateTime(Date updateTime) {
    	this.updateTime = updateTime;
    }
    /**
    * 获取更新时间起始值
    * @return updateTimeStart 更新时间起始值
    */
    public Date getUpdateTimeStart() {
    	return updateTimeStart;
    }
    /**
    * 设置更新时间起始值
    * @param updateTimeStart 更新时间起始值
    */
    public void setUpdateTimeStart(Date updateTimeStart) {
    	this.updateTimeStart = updateTimeStart;
    }
    /**
    * 获取更新时间结束值
    * @return updateTimeEnd 更新时间结束值
    */
    public Date getUpdateTimeEnd() {
    	return updateTimeEnd;
    }
    /**
    * 设置更新时间结束值
    * @param updateTimeEnd 更新时间结束值
    */
    public void setUpdateTimeEnd(Date updateTimeEnd) {
    	this.updateTimeEnd = updateTimeEnd;
    }
}
