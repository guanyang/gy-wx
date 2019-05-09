package org.gy.framework.bo;

import java.util.Date;

import org.gy.framework.util.generator.Pagination;

/**
 * 功能描述：微信通用用户信息BO
 * 
 * @Date 2017-10-30 10:04:29
 */
public class WxUserInfoBo extends Pagination{
    /**
    * 主键
    */
    private Long id;
    
    /**
    * 公众号APPID
    */
    private String appId;
    
    /**
    * 微信用户id
    */
    private String openId;
    
    /**
    * 用户统一标识
    */
    private String unionId;
    
    /**
    * 用户昵称
    */
    private String nickName;
    
    /**
    * 性别，1男，2女
    */
    private Integer sex;
    
    /**
    * 省份
    */
    private String province;
    
    /**
    * 城市
    */
    private String city;
    
    /**
    * 国家
    */
    private String country;
    
    /**
    * 用户语言
    */
    private String language;
    
    /**
    * 用户头像
    */
    private String headImgUrl;
    
    /**
    * 是否订阅，1是，0否
    */
    private Integer subscribe;
    
    /**
    * 订阅时间
    */
    private Date subscribeTime;
    
    /**
    * 订阅时间起始值
    */
    private Date subscribeTimeStart;
    /**
    * 订阅时间结束值
    */
    private Date subscribeTimeEnd;
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
    * 获取微信用户id
    * @return openId 微信用户id
    */
    public String getOpenId() {
    	return openId;
    }
    /**
    * 设置微信用户id
    * @param openId 微信用户id
    */
    public void setOpenId(String openId) {
    	this.openId = openId;
    }
    /**
    * 获取用户统一标识
    * @return unionId 用户统一标识
    */
    public String getUnionId() {
    	return unionId;
    }
    /**
    * 设置用户统一标识
    * @param unionId 用户统一标识
    */
    public void setUnionId(String unionId) {
    	this.unionId = unionId;
    }
    /**
    * 获取用户昵称
    * @return nickName 用户昵称
    */
    public String getNickName() {
    	return nickName;
    }
    /**
    * 设置用户昵称
    * @param nickName 用户昵称
    */
    public void setNickName(String nickName) {
    	this.nickName = nickName;
    }
    /**
    * 获取性别，1男，2女
    * @return sex 性别，1男，2女
    */
    public Integer getSex() {
    	return sex;
    }
    /**
    * 设置性别，1男，2女
    * @param sex 性别，1男，2女
    */
    public void setSex(Integer sex) {
    	this.sex = sex;
    }
    /**
    * 获取省份
    * @return province 省份
    */
    public String getProvince() {
    	return province;
    }
    /**
    * 设置省份
    * @param province 省份
    */
    public void setProvince(String province) {
    	this.province = province;
    }
    /**
    * 获取城市
    * @return city 城市
    */
    public String getCity() {
    	return city;
    }
    /**
    * 设置城市
    * @param city 城市
    */
    public void setCity(String city) {
    	this.city = city;
    }
    /**
    * 获取国家
    * @return country 国家
    */
    public String getCountry() {
    	return country;
    }
    /**
    * 设置国家
    * @param country 国家
    */
    public void setCountry(String country) {
    	this.country = country;
    }
    /**
    * 获取用户语言
    * @return language 用户语言
    */
    public String getLanguage() {
    	return language;
    }
    /**
    * 设置用户语言
    * @param language 用户语言
    */
    public void setLanguage(String language) {
    	this.language = language;
    }
    /**
    * 获取用户头像
    * @return headImgUrl 用户头像
    */
    public String getHeadImgUrl() {
    	return headImgUrl;
    }
    /**
    * 设置用户头像
    * @param headImgUrl 用户头像
    */
    public void setHeadImgUrl(String headImgUrl) {
    	this.headImgUrl = headImgUrl;
    }
    /**
    * 获取是否订阅，1是，0否
    * @return subscribe 是否订阅，1是，0否
    */
    public Integer getSubscribe() {
    	return subscribe;
    }
    /**
    * 设置是否订阅，1是，0否
    * @param subscribe 是否订阅，1是，0否
    */
    public void setSubscribe(Integer subscribe) {
    	this.subscribe = subscribe;
    }
    /**
    * 获取订阅时间
    * @return subscribeTime 订阅时间
    */
    public Date getSubscribeTime() {
    	return subscribeTime;
    }
    /**
    * 设置订阅时间
    * @param subscribeTime 订阅时间
    */
    public void setSubscribeTime(Date subscribeTime) {
    	this.subscribeTime = subscribeTime;
    }
    /**
    * 获取订阅时间起始值
    * @return subscribeTimeStart 订阅时间起始值
    */
    public Date getSubscribeTimeStart() {
    	return subscribeTimeStart;
    }
    /**
    * 设置订阅时间起始值
    * @param subscribeTimeStart 订阅时间起始值
    */
    public void setSubscribeTimeStart(Date subscribeTimeStart) {
    	this.subscribeTimeStart = subscribeTimeStart;
    }
    /**
    * 获取订阅时间结束值
    * @return subscribeTimeEnd 订阅时间结束值
    */
    public Date getSubscribeTimeEnd() {
    	return subscribeTimeEnd;
    }
    /**
    * 设置订阅时间结束值
    * @param subscribeTimeEnd 订阅时间结束值
    */
    public void setSubscribeTimeEnd(Date subscribeTimeEnd) {
    	this.subscribeTimeEnd = subscribeTimeEnd;
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
