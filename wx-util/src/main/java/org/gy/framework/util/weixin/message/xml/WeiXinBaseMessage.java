package org.gy.framework.util.weixin.message.xml;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * 
 * 功能描述: 接收到的消息
 * 
 * @version 2.0.0
 * @author yanchangyou(15070440)
 */
public class WeiXinBaseMessage implements WeiXinMessage {

    /**
     * 接收方帐号（收到的OpenID）
     */
    @XStreamAlias("ToUserName")
    protected String toUserName;

    /**
     * 开发者微信号
     */
    @XStreamAlias("FromUserName")
    protected String fromUserName;

    /**
     * 消息创建时间 （整型）
     */
    @XStreamAlias("CreateTime")
    protected Long createTime;

    /**
     * 消息类型
     */
    @XStreamAlias("MsgType")
    protected String msgType;

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

}
