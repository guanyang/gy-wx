/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: MenuViewEventRequestMessage.java
 *
 * @Author gy
 * @Date 2016年8月21日下午4:35:41
 */
package org.gy.framework.util.weixin.message.xml.request;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 功能描述：菜单跳转链接事件
 * 
 * @Author gy
 * @Date 2016年8月21日下午4:35:41
 */
@XStreamAlias("xml")
public class MenuViewEventRequestMessage extends BaseEventRequest {

    @XStreamAlias("EventKey")
    private String eventKey;

    @XStreamAlias("MenuId")
    private String menuId;

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
     * 获取menuId
     * 
     * @return menuId menuId
     */
    public String getMenuId() {
        return menuId;
    }

    /**
     * 设置menuId
     * 
     * @param menuId menuId
     */
    public void setMenuId(String menuId) {
        this.menuId = menuId;
    }

}
