package org.gy.framework.util.weixin.message.xml.response;

import org.gy.framework.util.weixin.message.xml.WeiXinBaseMessage;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

/**
 * 
 * 功能描述: 响应消息
 * 
 */
public class WeiXinResponse extends WeiXinBaseMessage {

    @XStreamOmitField
    private String innerMessageType;// 内部消息类型

    public String getInnerMessageType() {
        return innerMessageType;
    }

    public void setInnerMessageType(String innerMessageType) {
        this.innerMessageType = innerMessageType;
    }
}
