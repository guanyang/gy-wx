/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: TemplateEventRequestMessage.java
 *
 * @Author gy
 * @Date 2016年8月21日下午11:26:46
 */
package org.gy.framework.util.weixin.message.xml.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 功能描述：模板消息发送结束事件消息
 * 
 * @Author gy
 * @Date 2016年8月21日下午11:26:46
 */
@XStreamAlias("xml")
public class TemplateEventRequestMessage extends BaseEventRequest {

    /**
     * 消息id，64位整型
     */
    @XStreamAlias("MsgID")
    private Long   msgId;

    /**
     * 发送状态
     */
    @XStreamAlias("Status")
    private String status;

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

    /**
     * 获取 status
     * 
     * @return status status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置 status
     * 
     * @param status status
     */
    public void setStatus(String status) {
        this.status = status;
    }

}
