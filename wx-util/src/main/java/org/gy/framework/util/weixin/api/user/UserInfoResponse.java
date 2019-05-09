/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: UserInfoResponse.java
 *
 * @Author gy
 * @Date 2016年8月21日上午11:26:19
 */
package org.gy.framework.util.weixin.api.user;

import org.codehaus.jackson.annotate.JsonProperty;

import org.gy.framework.util.weixin.util.GeneralResponse;

/**
 * 功能描述：用户信息响应实体
 * 
 * @Author gy
 * @Date 2016年8月21日上午11:26:19
 */
public class UserInfoResponse extends GeneralResponse {

    /**
     * 是否订阅公众号，1订阅，0未订阅
     */
    @JsonProperty(value = "subscribe")
    private Integer subscribe;

    /**
     * 用户的标识
     */
    @JsonProperty(value = "openid")
    private String  openId;

    /**
     * 用户昵称
     */
    @JsonProperty(value = "nickname")
    private String  nickName;

    /**
     * 用户性别，1男性，2女性，0未知
     */
    @JsonProperty(value = "sex")
    private Integer sex;
    /**
     * 用户所在国家
     */
    @JsonProperty(value = "country")
    private String  country;
    /**
     * 用户所在城市
     */
    @JsonProperty(value = "city")
    private String  city;
    /**
     * 用户所在省份
     */
    @JsonProperty(value = "province")
    private String  province;
    /**
     * 用户的语言
     */
    @JsonProperty(value = "language")
    private String  language;
    /**
     * 用户头像
     * 
     */
    @JsonProperty(value = "headimgurl")
    private String  headImgUrl;
    /**
     * 用户关注时间
     */
    @JsonProperty(value = "subscribe_time")
    private Long    subscribeTime;

    /**
     * 用户统一标识
     * 
     */
    @JsonProperty(value = "unionid")
    private String  unionId;

    /**
     * 对粉丝的备注
     * 
     */
    @JsonProperty(value = "remark")
    private String  remark;

    /**
     * 用户所在的分组ID
     * 
     */
    @JsonProperty(value = "groupid")
    private String  groupId;

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Long getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Long subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

}
