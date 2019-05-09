/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: UnSubscribeEventRequestMessage.java
 *
 * @Author gy
 * @Date 2016年8月21日下午4:10:55
 */
package org.gy.framework.util.weixin.message.xml.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 功能描述：取消订阅事件消息
 * 
 * @Author gy
 * @Date 2016年8月21日下午4:10:55
 */
@XStreamAlias("xml")
public class UnSubscribeEventRequestMessage extends BaseEventRequest {

    @XStreamAlias("EventKey")
    private String eventKey;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

}
