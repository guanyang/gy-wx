/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: ScanEventRequestMessage.java
 *
 * @Author gy
 * @Date 2016年8月21日下午4:09:04
 */
package org.gy.framework.util.weixin.message.xml.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 功能描述：用户已关注时的扫码事件推送
 * 
 * @Author gy
 * @Date 2016年8月21日下午4:09:04
 */
@XStreamAlias("xml")
public class ScanEventRequestMessage extends BaseEventRequest {

    @XStreamAlias("EventKey")
    private String eventKey;

    @XStreamAlias("Ticket")
    private String ticket;

    /**
     * 获取eventKey
     * 
     * @return eventKey eventKey
     */
    public String getEventKey() {
        return eventKey;
    }

    /**
     * 设置eventKey
     * 
     * @param eventKey eventKey
     */
    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    /**
     * 获取ticket
     * 
     * @return ticket ticket
     */
    public String getTicket() {
        return ticket;
    }

    /**
     * 设置ticket
     * 
     * @param ticket ticket
     */
    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

}
