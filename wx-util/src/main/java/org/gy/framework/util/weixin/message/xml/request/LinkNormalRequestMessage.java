package org.gy.framework.util.weixin.message.xml.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("xml")
public class LinkNormalRequestMessage extends BaseNormalRequest {

    /**
     * 消息标题
     */
    @XStreamAlias("Title")
    private String title;
    /**
     * 消息描述
     */
    @XStreamAlias("Description")
    private String description;

    /**
     * 消息链接
     */
    @XStreamAlias("Url")
    private String Url;

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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

}
