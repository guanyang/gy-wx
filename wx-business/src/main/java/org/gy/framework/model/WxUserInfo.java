package org.gy.framework.model;

import java.util.Date;

public class WxUserInfo {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 公众号APPID
     *
     * @mbggenerated
     */
    private String appId;

    /**
     * 微信用户id
     *
     * @mbggenerated
     */
    private String openId;

    /**
     * 用户统一标识
     *
     * @mbggenerated
     */
    private String unionId;

    /**
     * 用户昵称
     *
     * @mbggenerated
     */
    private String nickName;

    /**
     * 性别，1男，2女
     *
     * @mbggenerated
     */
    private Integer sex;

    /**
     * 省份
     *
     * @mbggenerated
     */
    private String province;

    /**
     * 城市
     *
     * @mbggenerated
     */
    private String city;

    /**
     * 国家
     *
     * @mbggenerated
     */
    private String country;

    /**
     * 用户语言
     *
     * @mbggenerated
     */
    private String language;

    /**
     * 用户头像
     *
     * @mbggenerated
     */
    private String headImgUrl;

    /**
     * 是否订阅，1是，0否
     *
     * @mbggenerated
     */
    private Integer subscribe;

    /**
     * 订阅时间
     *
     * @mbggenerated
     */
    private Date subscribeTime;

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
     * 获取  公众号APPID
     *
     * @return APP_ID 公众号APPID
     *
     * @mbggenerated
     */
    public String getAppId() {
        return appId;
    }

    /**
     * 设置  公众号APPID
     *
     * @param appId 公众号APPID
     *
     * @mbggenerated
     */
    public void setAppId(String appId) {
        this.appId = appId == null ? null : appId.trim();
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
     * 获取  用户统一标识
     *
     * @return UNION_ID 用户统一标识
     *
     * @mbggenerated
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * 设置  用户统一标识
     *
     * @param unionId 用户统一标识
     *
     * @mbggenerated
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
    }

    /**
     * 获取  用户昵称
     *
     * @return NICK_NAME 用户昵称
     *
     * @mbggenerated
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置  用户昵称
     *
     * @param nickName 用户昵称
     *
     * @mbggenerated
     */
    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    /**
     * 获取  性别，1男，2女
     *
     * @return SEX 性别，1男，2女
     *
     * @mbggenerated
     */
    public Integer getSex() {
        return sex;
    }

    /**
     * 设置  性别，1男，2女
     *
     * @param sex 性别，1男，2女
     *
     * @mbggenerated
     */
    public void setSex(Integer sex) {
        this.sex = sex;
    }

    /**
     * 获取  省份
     *
     * @return PROVINCE 省份
     *
     * @mbggenerated
     */
    public String getProvince() {
        return province;
    }

    /**
     * 设置  省份
     *
     * @param province 省份
     *
     * @mbggenerated
     */
    public void setProvince(String province) {
        this.province = province == null ? null : province.trim();
    }

    /**
     * 获取  城市
     *
     * @return CITY 城市
     *
     * @mbggenerated
     */
    public String getCity() {
        return city;
    }

    /**
     * 设置  城市
     *
     * @param city 城市
     *
     * @mbggenerated
     */
    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    /**
     * 获取  国家
     *
     * @return COUNTRY 国家
     *
     * @mbggenerated
     */
    public String getCountry() {
        return country;
    }

    /**
     * 设置  国家
     *
     * @param country 国家
     *
     * @mbggenerated
     */
    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    /**
     * 获取  用户语言
     *
     * @return LANGUAGE 用户语言
     *
     * @mbggenerated
     */
    public String getLanguage() {
        return language;
    }

    /**
     * 设置  用户语言
     *
     * @param language 用户语言
     *
     * @mbggenerated
     */
    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }

    /**
     * 获取  用户头像
     *
     * @return HEAD_IMG_URL 用户头像
     *
     * @mbggenerated
     */
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    /**
     * 设置  用户头像
     *
     * @param headImgUrl 用户头像
     *
     * @mbggenerated
     */
    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
    }

    /**
     * 获取  是否订阅，1是，0否
     *
     * @return SUBSCRIBE 是否订阅，1是，0否
     *
     * @mbggenerated
     */
    public Integer getSubscribe() {
        return subscribe;
    }

    /**
     * 设置  是否订阅，1是，0否
     *
     * @param subscribe 是否订阅，1是，0否
     *
     * @mbggenerated
     */
    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    /**
     * 获取  订阅时间
     *
     * @return SUBSCRIBE_TIME 订阅时间
     *
     * @mbggenerated
     */
    public Date getSubscribeTime() {
        return subscribeTime;
    }

    /**
     * 设置  订阅时间
     *
     * @param subscribeTime 订阅时间
     *
     * @mbggenerated
     */
    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
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