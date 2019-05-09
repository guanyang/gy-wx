package org.gy.framework.util.weixin.message.json.custom;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 功能描述：客服文本消息
 * 
 */
public class CustomTextMessage extends CustomBaseMessage {

    /**
     * 文本内容
     */
    @JsonProperty(value = "text")
    private CustomText text;

    /**
     * 获取文本内容
     * 
     * @return text 文本内容
     */
    public CustomText getText() {
        return text;
    }

    /**
     * 设置文本内容
     * 
     * @param text 文本内容
     */
    public void setText(CustomText text) {
        this.text = text;
    }

}
