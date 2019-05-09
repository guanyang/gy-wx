package org.gy.framework.util.weixin.message.xml.response;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 功能描述: 图片响应消息
 * 
 */
@XStreamAlias("xml")
public class ImageResponseMessage extends WeiXinResponse {

    /**
     * 媒体素材id
     */
    @XStreamAlias("MediaId")
    private String mediaId;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

}
