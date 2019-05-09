package org.gy.framework.util.weixin.message.json.custom;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 功能描述：客服图文消息
 * 
 */
public class CustomNewsMessage extends CustomBaseMessage {

    /**
     * 图文消息
     */
    @JsonProperty(value = "news")
    private CustomNews news;

    /**
     * 获取图文消息
     * 
     * @return news 图文消息
     */
    public CustomNews getNews() {
        return news;
    }

    /**
     * 设置图文消息
     * 
     * @param news 图文消息
     */
    public void setNews(CustomNews news) {
        this.news = news;
    }

}
