package org.gy.framework.util.weixin.util;

/**
 * 
 * 功能描述: 微信常量
 * 
 */
public class WeiXinConstantUtil {

    /**
     * 消息类型：文本
     */
    public static final String MESSAGE_TYPE_TEXT                = "text";
    /**
     * 消息类型：图片
     */
    public static final String MESSAGE_TYPE_IMAGE               = "image";
    /**
     * 消息类型：语音
     */
    public static final String MESSAGE_TYPE_VOICE               = "voice";
    /**
     * 消息类型：视频
     */
    public static final String MESSAGE_TYPE_VIDEO               = "video";
    /**
     * 消息类型：音乐
     */
    public static final String MESSAGE_TYPE_MUSIC               = "music";
    /**
     * 消息类型：图文
     */
    public static final String MESSAGE_TYPE_NEWS                = "news";
    /**
     * 消息类型：卡券
     */
    public static final String MESSAGE_TYPE_WXCARD              = "wxcard";
    /**
     * 消息类型：小视频
     */
    public static final String MESSAGE_TYPE_SHORTVIDEO          = "shortvideo";
    /**
     * 消息类型：地理位置
     */
    public static final String MESSAGE_TYPE_LOCATION            = "location";
    /**
     * 消息类型：链接
     */
    public static final String MESSAGE_TYPE_LINK                = "link";
    /**
     * 消息类型：事件
     */
    public static final String MESSAGE_TYPE_EVENT               = "event";
    /**
     * 事件类型：订阅/未关注扫码事件
     */
    public static final String EVENT_TYPE_SUBSCRIBE             = "subscribe";
    /**
     * 事件类型：取消订阅
     */
    public static final String EVENT_TYPE_UNSUBSCRIBE           = "unsubscribe";
    /**
     * 事件类型：上报地理位置
     */
    public static final String EVENT_TYPE_LOCATION              = "LOCATION";
    /**
     * 事件类型：已关注扫码事件
     */
    public static final String EVENT_TYPE_SCAN                  = "SCAN";
    /**
     * 事件类型：点击菜单拉取消息事件
     */
    public static final String EVENT_TYPE_CLICK                 = "CLICK";
    /**
     * 事件类型：点击菜单跳转链接事件
     */
    public static final String EVENT_TYPE_VIEW                  = "VIEW";
    /**
     * 事件类型：模板消息发送结束事件
     */
    public static final String EVENT_TYPE_TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";

    /**
     * 菜单类型：跳转URL
     */
    public static final String MENU_TYPE_VIEW                   = "view";
    /**
     * 菜单类型：点击推事件
     */
    public static final String MENU_TYPE_CLICK                  = "click";
    /**
     * 菜单类型：小程序
     */
    public static final String MENU_TYPE_MINIPROGRAM            = "miniprogram";
    /**
     * 菜单类型：扫码推事件
     */
    public static final String MENU_TYPE_SCANCODE_PUSH          = "scancode_push";
    /**
     * 菜单类型：扫码推事件且弹出“消息接收中”提示框
     */
    public static final String MENU_TYPE_SCANCODE_WAITMSG       = "scancode_waitmsg";
    /**
     * 菜单类型：弹出系统拍照发图
     */
    public static final String MENU_TYPE_PIC_SYSPHOTO           = "pic_sysphoto";
    /**
     * 菜单类型：弹出拍照或者相册发图
     */
    public static final String MENU_TYPE_PIC_PHOTO_OR_ALBUM     = "pic_photo_or_album";
    /**
     * 菜单类型：弹出微信相册发图器
     */
    public static final String MENU_TYPE_PIC_WEIXIN             = "pic_weixin";
    /**
     * 菜单类型：下发消息（除文本消息） 微信服务器会将开发者填写的永久素材id对应的素材下发给用户，永久素材类型可以是图片、音频、视频、图文消息
     */
    public static final String MENU_TYPE_MEDIA_ID               = "media_id";
    /**
     * 菜单类型：跳转图文消息URL
     */
    public static final String MENU_TYPE_VIEW_LIMITED           = "view_limited";

    /**
     * 微信关注回复key
     */
    public static final String WEIXIN_SUBSCRIBE_KEY             = "9998";

    /**
     * 微信默认回复key
     */
    public static final String WEIXIN_DEFAULT_KEY               = "9999";

    /**
     * 微信回复类型：空 回复
     */
    public static final String WEIXIN_REPLY_TYPE_EMPTY          = "-1";
    /**
     * 日志分隔符
     */
    public static final String WEIXIN_LOG_SEPARATOR             = "@@";

    private WeiXinConstantUtil() {

    }

}
