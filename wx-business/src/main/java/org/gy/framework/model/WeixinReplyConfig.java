package org.gy.framework.model;

import java.util.Date;

public class WeixinReplyConfig {
    /**
     * 主键
     *
     * @mbggenerated
     */
    private Long id;

    /**
     * 关键字
     *
     * @mbggenerated
     */
    private String keywords;

    /**
     * 标题
     *
     * @mbggenerated
     */
    private String title;

    /**
     * 描述
     *
     * @mbggenerated
     */
    private String description;

    /**
     * 回复文本
     *
     * @mbggenerated
     */
    private String replyText;

    /**
     * 回复链接
     *
     * @mbggenerated
     */
    private String replyLink;

    /**
     * 回复图片
     *
     * @mbggenerated
     */
    private String replyImg;

    /**
     * 回复类型,0:默认回复,1:文本,2:链接,3:关注回复,4:图文
     *
     * @mbggenerated
     */
    private Byte replyType;

    /**
     * 是否生效，1是，0否
     *
     * @mbggenerated
     */
    private Byte enable;

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
     * 获取  关键字
     *
     * @return KEYWORDS 关键字
     *
     * @mbggenerated
     */
    public String getKeywords() {
        return keywords;
    }

    /**
     * 设置  关键字
     *
     * @param keywords 关键字
     *
     * @mbggenerated
     */
    public void setKeywords(String keywords) {
        this.keywords = keywords == null ? null : keywords.trim();
    }

    /**
     * 获取  标题
     *
     * @return TITLE 标题
     *
     * @mbggenerated
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置  标题
     *
     * @param title 标题
     *
     * @mbggenerated
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取  描述
     *
     * @return DESCRIPTION 描述
     *
     * @mbggenerated
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置  描述
     *
     * @param description 描述
     *
     * @mbggenerated
     */
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    /**
     * 获取  回复文本
     *
     * @return REPLY_TEXT 回复文本
     *
     * @mbggenerated
     */
    public String getReplyText() {
        return replyText;
    }

    /**
     * 设置  回复文本
     *
     * @param replyText 回复文本
     *
     * @mbggenerated
     */
    public void setReplyText(String replyText) {
        this.replyText = replyText == null ? null : replyText.trim();
    }

    /**
     * 获取  回复链接
     *
     * @return REPLY_LINK 回复链接
     *
     * @mbggenerated
     */
    public String getReplyLink() {
        return replyLink;
    }

    /**
     * 设置  回复链接
     *
     * @param replyLink 回复链接
     *
     * @mbggenerated
     */
    public void setReplyLink(String replyLink) {
        this.replyLink = replyLink == null ? null : replyLink.trim();
    }

    /**
     * 获取  回复图片
     *
     * @return REPLY_IMG 回复图片
     *
     * @mbggenerated
     */
    public String getReplyImg() {
        return replyImg;
    }

    /**
     * 设置  回复图片
     *
     * @param replyImg 回复图片
     *
     * @mbggenerated
     */
    public void setReplyImg(String replyImg) {
        this.replyImg = replyImg == null ? null : replyImg.trim();
    }

    /**
     * 获取  回复类型,0:默认回复,1:文本,2:链接,3:关注回复,4:图文
     *
     * @return REPLY_TYPE 回复类型,0:默认回复,1:文本,2:链接,3:关注回复,4:图文
     *
     * @mbggenerated
     */
    public Byte getReplyType() {
        return replyType;
    }

    /**
     * 设置  回复类型,0:默认回复,1:文本,2:链接,3:关注回复,4:图文
     *
     * @param replyType 回复类型,0:默认回复,1:文本,2:链接,3:关注回复,4:图文
     *
     * @mbggenerated
     */
    public void setReplyType(Byte replyType) {
        this.replyType = replyType;
    }

    /**
     * 获取  是否生效，1是，0否
     *
     * @return ENABLE 是否生效，1是，0否
     *
     * @mbggenerated
     */
    public Byte getEnable() {
        return enable;
    }

    /**
     * 设置  是否生效，1是，0否
     *
     * @param enable 是否生效，1是，0否
     *
     * @mbggenerated
     */
    public void setEnable(Byte enable) {
        this.enable = enable;
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