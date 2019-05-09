package org.gy.framework.util.weixin.message.json.custom;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 功能描述：文本内容封装
 *
 */
public class CustomText {
    
    /**
     * 文本消息内容
     */
    @JsonProperty(value = "content")
    private String content;

    /**
     * 获取文本消息内容
     * @return content 文本消息内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置文本消息内容
     * @param content 文本消息内容
     */
    public void setContent(String content) {
        this.content = content;
    }
    

}
