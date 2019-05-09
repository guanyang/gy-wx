/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: TemplateResponse.java
 *
 * @Author gy
 * @Date 2016年8月21日下午10:37:51
 */
package org.gy.framework.util.weixin.api.template;

import org.gy.framework.util.weixin.util.GeneralResponse;

/**
 * 功能描述：模板消息返回值
 * 
 * @Author gy
 * @Date 2016年8月21日下午10:37:51
 */
public class TemplateResponse extends GeneralResponse {

    /**
     * 消息id
     */
    private Long msgid;

    /**
     * 获取 msgid
     * 
     * @return msgid msgid
     */
    public Long getMsgid() {
        return msgid;
    }

    /**
     * 设置 msgid
     * 
     * @param msgid msgid
     */
    public void setMsgid(Long msgid) {
        this.msgid = msgid;
    }

}
