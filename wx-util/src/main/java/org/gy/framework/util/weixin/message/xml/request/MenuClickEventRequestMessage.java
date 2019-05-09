/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: MenuEventRequestMessage.java
 *
 * @Author gy
 * @Date 2016年8月21日下午3:40:18
 */
package org.gy.framework.util.weixin.message.xml.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 功能描述：菜单拉取消息事件
 * 
 * @Author gy
 * @Date 2016年8月21日下午3:40:18
 */
@XStreamAlias("xml")
public class MenuClickEventRequestMessage extends BaseEventRequest {

    @XStreamAlias("EventKey")
    private String eventKey;

    @XStreamAlias("MenuId")
    private String menuId;

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

    public String getMenuId() {
        return menuId;
    }

    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

}
