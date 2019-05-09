package org.gy.framework.util.weixin.message.json.custom;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 功能描述：客服消息基类
 * 
 */
public class CustomBaseMessage {

    /**
     * 普通用户openid
     */
    @JsonProperty(value = "touser")
    private String touser;

    /**
     * 消息类型，文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，图文消息为news，卡券为wxcard
     */
    @JsonProperty(value = "msgtype")
    private String msgtype;

    /**
     * 获取普通用户openid
     * 
     * @return touser 普通用户openid
     */
    public String getTouser() {
        return touser;
    }

    /**
     * 设置普通用户openid
     * 
     * @param touser 普通用户openid
     */
    public void setTouser(String touser) {
        this.touser = touser;
    }

    /**
     * 获取消息类型，文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，图文消息为news，卡券为wxcard
     * 
     * @return msgtype 消息类型，文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，图文消息为news，卡券为wxcard
     */
    public String getMsgtype() {
        return msgtype;
    }

    /**
     * 设置消息类型，文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，图文消息为news，卡券为wxcard
     * 
     * @param msgtype 消息类型，文本为text，图片为image，语音为voice，视频消息为video，音乐消息为music，图文消息为news，卡券为wxcard
     */
    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

}
