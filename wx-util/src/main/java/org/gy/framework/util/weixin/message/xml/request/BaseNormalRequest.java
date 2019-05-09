package org.gy.framework.util.weixin.message.xml.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class BaseNormalRequest extends WeiXinRequest {

    /**
     * 消息id，64位整型
     */
    @XStreamAlias("MsgId")
    private Long msgId;

    /**
     * 获取消息id，64位整型
     * 
     * @return msgId 消息id，64位整型
     */
    public Long getMsgId() {
        return msgId;
    }

    /**
     * 设置消息id，64位整型
     * 
     * @param msgId 消息id，64位整型
     */
    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }
}
