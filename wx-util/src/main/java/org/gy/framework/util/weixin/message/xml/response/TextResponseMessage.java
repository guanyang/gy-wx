package org.gy.framework.util.weixin.message.xml.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 功能描述: 文本响应消息
 * 
 */
@XStreamAlias("xml")
public class TextResponseMessage extends WeiXinResponse {

    /**
     * 文本内容
     */
    @XStreamAlias("Content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
