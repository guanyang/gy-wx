package org.gy.framework.util.weixin.message.xml.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class Music {

    /**
     * 音乐标题
     */
    @XStreamAlias("Title")
    private String title;

    /**
     * 音乐描述
     */
    @XStreamAlias("Description")
    private String description;

    /**
     * 音乐链接
     */
    @XStreamAlias("MusicURL")
    private String musicURL;

    /**
     * 高质量音乐链接，WIFI环境优先使用该链接播放音乐
     */
    @XStreamAlias("HQMusicUrl")
    private String hQMusicUrl;

    /**
     * 缩略图的媒体id
     */
    @XStreamAlias("ThumbMediaId")
    private String thumbMediaId;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMusicURL() {
        return musicURL;
    }

    public void setMusicURL(String musicURL) {
        this.musicURL = musicURL;
    }

    public String gethQMusicUrl() {
        return hQMusicUrl;
    }

    public void sethQMusicUrl(String hQMusicUrl) {
        this.hQMusicUrl = hQMusicUrl;
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }

}
