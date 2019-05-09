package org.gy.framework.util.weixin.message.xml;

/**
 * 
 * 功能描述: 微信消息
 * 
 * @version 2.0.0
 * @author yanchangyou(15070440)
 */
public interface WeiXinMessage {

    /**
     * 
     * 功能描述: 消息接收用户
     * 
     * @return String
     * @version 2.0.0
     * @author yanchangyou
     */
    public String getToUserName();

    /**
     * 
     * 功能描述: 发送用户名
     * 
     * @return String
     * @version 2.0.0
     * @author yanchangyou
     */
    public String getFromUserName();

    /**
     * 
     * 功能描述: 创建时间
     * 
     * @return Long
     * @version 2.0.0
     * @author yanchangyou
     */
    public Long getCreateTime();

    /**
     * 
     * 功能描述: 消息类型
     * 
     * @return String
     * @version 2.0.0
     * @author yanchangyou
     */
    public String getMsgType();

}
