package org.gy.framework.util.weixin.message.json.custom;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 功能描述：单条图文实体
 * 
 */
public class CustomArticle {

    /**
     * 图文消息/视频消息/音乐消息的标题
     */
    @JsonProperty(value = "title")
    private String title;
    
    /**
     * 图文消息/视频消息/音乐消息的描述
     */
    @JsonProperty(value = "description")
    private String description;

    /**
     * 图文消息被点击后跳转的链接
     */
    @JsonProperty(value = "url")
    private String url;

    /**
     * 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
     */
    @JsonProperty(value = "picurl")
    private String picurl;

    /**
     * 获取图文消息视频消息音乐消息的标题
     * @return title 图文消息视频消息音乐消息的标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置图文消息视频消息音乐消息的标题
     * @param title 图文消息视频消息音乐消息的标题
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 获取图文消息视频消息音乐消息的描述
     * @return description 图文消息视频消息音乐消息的描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置图文消息视频消息音乐消息的描述
     * @param description 图文消息视频消息音乐消息的描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取图文消息被点击后跳转的链接
     * @return url 图文消息被点击后跳转的链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置图文消息被点击后跳转的链接
     * @param url 图文消息被点击后跳转的链接
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 获取图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640320，小图8080
     * @return picurl 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640320，小图8080
     */
    public String getPicurl() {
        return picurl;
    }

    /**
     * 设置图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640320，小图8080
     * @param picurl 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640320，小图8080
     */
    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }
    
    

}
