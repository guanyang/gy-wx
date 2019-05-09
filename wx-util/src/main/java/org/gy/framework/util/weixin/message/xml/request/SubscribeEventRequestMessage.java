/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: SubscribeEventRequestMessage.java
 *
 * @Author gy
 * @Date 2016年8月21日下午4:05:08
 */
package org.gy.framework.util.weixin.message.xml.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 功能描述：订阅、未关注扫码事件消息
 * 
 * @Author gy
 * @Date 2016年8月21日下午4:05:08
 */
@XStreamAlias("xml")
public class SubscribeEventRequestMessage extends BaseEventRequest {

    @XStreamAlias("EventKey")
    private String eventKey;

    @XStreamAlias("Ticket")
    private String ticket;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

}
