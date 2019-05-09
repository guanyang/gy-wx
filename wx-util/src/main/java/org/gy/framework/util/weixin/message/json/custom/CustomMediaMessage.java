package org.gy.framework.util.weixin.message.json.custom;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 功能描述：客服图片消息
 * 
 */
public class CustomMediaMessage extends CustomBaseMessage {
    
    /**
     * 素材内容
     */
    @JsonProperty(value = "image")
    private CustomMedia customMedia;

    public CustomMedia getCustomMedia() {
        return customMedia;
    }

    public void setCustomMedia(CustomMedia customMedia) {
        this.customMedia = customMedia;
    }
    
    

}
