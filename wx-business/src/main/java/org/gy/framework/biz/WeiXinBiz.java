package org.gy.framework.biz;

import java.util.Date;
import java.util.concurrent.ThreadPoolExecutor;

import org.apache.commons.lang3.StringUtils;
import org.gy.framework.model.WxAppConfig;
import org.gy.framework.model.WxReplyConfig;
import org.gy.framework.model.WxReplyLog;
import org.gy.framework.model.WxUserInfo;
import org.gy.framework.model.WxUserRecord;
import org.gy.framework.util.cache.RedisConfig;
import org.gy.framework.util.cache.RedisConfig.CacheKey;
import org.gy.framework.util.convert.BeanUtil;
import org.gy.framework.util.jedis.DistributedLock;
import org.gy.framework.util.json.JacksonMapper;
import org.gy.framework.util.regex.RegexUtil;
import org.gy.framework.util.weixin.api.ticket.JsApiTicketService;
import org.gy.framework.util.weixin.api.ticket.model.JsApiTicketResponse;
import org.gy.framework.util.weixin.api.token.TokenRefresh;
import org.gy.framework.util.weixin.api.token.TokenResponse;
import org.gy.framework.util.weixin.api.user.UserInfoGet;
import org.gy.framework.util.weixin.api.user.UserInfoResponse;
import org.gy.framework.util.weixin.exception.WeiXinException;
import org.gy.framework.util.weixin.message.xml.request.LinkNormalRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.LocationNormalRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.MenuClickEventRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.MenuViewEventRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.ScanEventRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.SubscribeEventRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.TextNormalRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.UnSubscribeEventRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.WeiXinRequest;
import org.gy.framework.util.weixin.message.xml.response.TextResponseMessage;
import org.gy.framework.util.weixin.message.xml.response.WeiXinResponse;
import org.gy.framework.util.weixin.service.WeiXinCoreService;
import org.gy.framework.util.weixin.util.WeiXinConstantUtil;
import org.gy.framework.util.weixin.util.WeiXinUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeiXinBiz extends BaseBiz implements WeiXinCoreService {

    public static final String DEFAULT_SCENE_PREFIX = "qrscene_";

    @Autowired
    private ThreadPoolExecutor executor;

    @Autowired
    private WxUserRecordBiz    wxUserRecordBiz;

    @Autowired
    private WxReplyConfigBiz   wxReplyConfigBiz;

    @Autowired
    private WxReplyLogBiz      wxReplyLogBiz;

    @Autowired
    private WxUserInfoBiz      wxUserInfoBiz;

    @Autowired
    private WxAppConfigBiz     wxAppConfigBiz;

    /**
     * 功能描述:获取指定公众号JSAPI票据
     * 
     * @return
     */
    public String getJsApiTicketByAppId(String appId) {
        final String redisKey = RedisConfig.getKey(CacheKey.GY_CACHE_JSAPI_TICKET_KEY, appId);
        String result = redisClient.get(redisKey);
        if (StringUtils.isNotBlank(result)) {
            return result;
        }
        /********* 分布式锁 ********/
        // 必须添加分布式锁，避免高并发时多次刷新access_token
        DistributedLock lock = new DistributedLock(redisClient, redisKey, 10000);
        boolean flag = lock.tryLock(5000, 200);
        if (!flag) {
            return null;
        }
        try {
            result = redisClient.get(redisKey);
            if (StringUtils.isBlank(result)) {
                String accessToken = getTokenByAppId(appId);
                JsApiTicketResponse response = JsApiTicketService.getJsApiTicket(accessToken);
                if (response.isSuccess() && StringUtils.isNotBlank(response.getTicket())) {
                    result = response.getTicket();
                    redisClient.setex(redisKey, CacheKey.GY_CACHE_JSAPI_TICKET_KEY.getExpireTime(), result);
                }
            }
        } catch (Exception e) {
            logger.error("获取jsApiTicket异常：" + e.getMessage(), e);
        } finally {
            lock.unLock();// 释放锁
        }
        return result;

    }

    /**
     * 功能描述:获取指定公众号token
     * 
     * @param appId
     * @return
     */
    public String getTokenByAppId(String appId) {
        final String redisKey = RedisConfig.getKey(CacheKey.GY_CACHE_WX_ACCESS_TOKEN_KEY, appId);
        String result = redisClient.get(redisKey);
        if (StringUtils.isNotBlank(result) && !"null".equals(result.trim())) {
            return result;
        }
        /********* 分布式锁 ********/
        // 必须添加分布式锁，避免高并发时多次刷新access_token
        DistributedLock lock = new DistributedLock(redisClient, redisKey, 10000);
        boolean flag = lock.tryLock(5000, 200);
        if (!flag) {
            return null;
        }
        try {
            result = redisClient.get(redisKey);
            if (StringUtils.isBlank(result)) {
                WxAppConfig config = wxAppConfigBiz.getWxAppConfigWithCache(appId);
                // 刷新access_token
                TokenResponse response = TokenRefresh.getAccessToken(config.getAppId(), config.getAppSecret());
                if (response != null && StringUtils.isNotBlank(response.getAsscessToken())) {
                    // 设置redis缓存
                    result = response.getAsscessToken();
                    int expireTime = CacheKey.GY_CACHE_WX_ACCESS_TOKEN_KEY.getExpireTime();
                    redisClient.setex(redisKey, expireTime, result);
                }
            }
        } finally {
            lock.unLock();// 释放锁
        }
        return result;
    }

    @Override
    public String getValidateToken(String appId) {
        WxAppConfig config = wxAppConfigBiz.getWxAppConfigWithCache(appId);
        if (config == null) {
            throw new WeiXinException(appId + "验证令牌未配置");
        }
        return config.getValidateToken();
    }

    @Override
    public String processRequest(WeiXinRequest message) {
        try {
            // 验证appId是否正确
            String appId = message.getAppId();
            WxAppConfig config = wxAppConfigBiz.getWxAppConfigWithCache(appId);
            if (config == null) {
                logger.error("开发者中心appId配置不正确：" + message.getAppId());
                return null;
            }
            // 生成响应对象
            WeiXinResponse responseMessage = dispatch(message);
            if (responseMessage != null) {
                return WeiXinUtil.bean2xml(responseMessage);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 
     * 功能描述: 分发消息
     * 
     */
    public WeiXinResponse dispatch(WeiXinRequest requestMessage) {

        WeiXinResponse responseMessage = null;
        // 记录用户操作
        final String openId = requestMessage.getFromUserName();
        WxUserRecord entity = new WxUserRecord();
        entity.setOpenId(openId);
        entity.setSubStatus("1");// 默认是关注
        entity.setAppId(requestMessage.getAppId());

        if (requestMessage instanceof TextNormalRequestMessage) {
            // 文本消息
            responseMessage = dealMessage((TextNormalRequestMessage) requestMessage);
        } else if (requestMessage instanceof LocationNormalRequestMessage) {
            // 位置消息
            // responseMessage = dealMessage((LocationNormalRequestMessage) requestMessage);//暂不处理
        } else if (requestMessage instanceof LinkNormalRequestMessage) {
            // 链接消息
            // responseMessage = dealMessage((LinkNormalRequestMessage) requestMessage);//暂不处理
        } else if (requestMessage instanceof SubscribeEventRequestMessage) {
            // 订阅、未关注扫描带参数二维码事件
            SubscribeEventRequestMessage message = (SubscribeEventRequestMessage) requestMessage;
            if (StringUtils.isNotBlank(message.getEventKey())) {
                entity.setSceneId(message.getEventKey().substring(DEFAULT_SCENE_PREFIX.length()));
            }
            responseMessage = dealMessage(message);
        } else if (requestMessage instanceof UnSubscribeEventRequestMessage) {
            // 取消订阅事件
            entity.setSubStatus("2");// 取消关注
            // 取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息

        } else if (requestMessage instanceof ScanEventRequestMessage) {
            // 用户已关注时扫描带参数二维码事件
            responseMessage = dealMessage((ScanEventRequestMessage) requestMessage);
        } else if (requestMessage instanceof MenuClickEventRequestMessage) {
            // 菜单拉取消息事件
            responseMessage = dealMessage((MenuClickEventRequestMessage) requestMessage);
        } else if (requestMessage instanceof MenuViewEventRequestMessage) {
            // 菜单跳转链接事件
            // 查看事件,跳转到具体的网页，不需要处理
        }
        addUserRecord(entity);
        return responseMessage;

    }

    public WeiXinResponse handlerReply(WeiXinRequest requestMessage,
                                       String key) {
        WeiXinResponse result = null;
        String appId = requestMessage.getAppId();
        WxReplyConfig config = wxReplyConfigBiz.getReplyConfigByKeywordsWithCache(appId, key);
        if (config != null) {
            Integer replyType = config.getReplyType();// 回复类型,1:文本,2:链接,3:图文
            if (replyType == 1) {
                TextResponseMessage responseMessage = new TextResponseMessage();
                responseMessage.setContent(config.getReplyText());
                responseMessage.setMsgType(WeiXinConstantUtil.MESSAGE_TYPE_TEXT);

                result = responseMessage;

            } else if (replyType == 2) {
                // 连接直接跳转，不用处理
            } else if (replyType == 3) {
                // 红包图文消息

            }
        } else {
            if (!WeiXinConstantUtil.WEIXIN_DEFAULT_KEY.equals(key)) {
                result = handlerReply(requestMessage, WeiXinConstantUtil.WEIXIN_DEFAULT_KEY);// 添加默认回复
            }
        }
        if (result != null) {
            WeiXinUtil.packageResponseMessage(requestMessage, result);
        }
        return result;
    }

    /**
     * 文本消息处理
     */
    private WeiXinResponse dealMessage(TextNormalRequestMessage requestMessage) {

        String keywords = requestMessage.getContent();// 根据关键字分发处理

        // 记录日志
        WxReplyLog log = wrapWeixinReplyLog(requestMessage);
        log.setContent(keywords);
        addReplyLog(log);

        return handlerReply(requestMessage, keywords);
    }

    /**
     * 菜单拉取消息事件处理
     */
    private WeiXinResponse dealMessage(MenuClickEventRequestMessage requestMessage) {
        // 菜单点击事件
        String key = requestMessage.getEventKey();
        return handlerReply(requestMessage, key);
    }

    /**
     * 订阅、未关注扫码事件处理
     */
    private WeiXinResponse dealMessage(SubscribeEventRequestMessage requestMessage) {
        // 异步保存用户信息
        String openId = requestMessage.getFromUserName();
        String appId = requestMessage.getAppId();
        addWxUserInfo(openId, appId);
        // 默认回复
        return handlerReply(requestMessage, WeiXinConstantUtil.WEIXIN_SUBSCRIBE_KEY);

    }

    /**
     * 功能描述: 异步保存用户信息
     * 
     * @param openId
     * @param appId
     */
    public void addWxUserInfo(final String openId,
                              final String appId) {
        try {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    UserInfoResponse response = getWeiXinUserInfo(openId, appId);
                    if (response != null && response.isSuccess()) {
                        WxUserInfo wxUserInfo = wrapWxUserInfo(response, appId);
                        wxUserInfoBiz.insertOrUpdate(wxUserInfo);// 保存用户信息
                    } else {
                        logger.error("获取用户信息有误：openId={},appId={},response={}", openId, appId, JacksonMapper.beanToJson(response));
                    }
                }
            });
        } catch (Exception e) {
            logger.error("添加微信用户信息异常：openId={},appId={}", openId, appId);
            logger.error("添加微信用户信息异常：" + e.getMessage(), e);
        }
    }

    /**
     * 功能描述: 组装用户信息
     * 
     * @param response
     * @return
     */
    private WxUserInfo wrapWxUserInfo(UserInfoResponse response,
                                      String appId) {
        WxUserInfo info = new WxUserInfo();
        BeanUtil.copyProperties(response, info);
        // 用户没有关注该公众号，拉取不到其余信息
        info.setAppId(appId);
        // 微信返回的时间戳只到秒，需要转换成毫秒
        if (response.getSubscribeTime() != null) {
            info.setSubscribeTime(new Date(response.getSubscribeTime() * 1000L));
        }
        info.setCreateTime(new Date());
        return info;
    }

    /**
     * 功能描述: 获取微信用户基本信息
     * 
     * @param openId
     * @return
     */
    private UserInfoResponse getWeiXinUserInfo(String openId,
                                               String appId) {
        String token = getTokenByAppId(appId);
        UserInfoResponse response = UserInfoGet.getUserInfo(token, openId);
        if (response != null && response.isSuccess()) {
            // 过滤名称中的特殊字符,emoji表情
            String name = RegexUtil.filterEmoji(response.getNickName());
            response.setNickName(name);
        }
        return response;
    }

    /**
     * 用户已关注时扫描带参数二维码事件
     */
    private WeiXinResponse dealMessage(ScanEventRequestMessage requestMessage) {
        // 默认回复
        return handlerReply(requestMessage, WeiXinConstantUtil.WEIXIN_DEFAULT_KEY);

    }

    private void addUserRecord(final WxUserRecord entity) {
        try {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    wxUserRecordBiz.insertOrUpdate(entity);
                }
            });
        } catch (Exception e) {
            logger.error("添加微信用户记录异常：" + e.getMessage(), e);
        }
    }

    /**
     * 位置消息处理
     * 
     */
    private WeiXinResponse dealMessage(LocationNormalRequestMessage requestMessage) {
        // 记录日志
        WxReplyLog log = wrapWeixinReplyLog(requestMessage);
        StringBuilder builder = new StringBuilder();
        builder.append(requestMessage.getLocationX()).append(WeiXinConstantUtil.WEIXIN_LOG_SEPARATOR);
        builder.append(requestMessage.getLocationY()).append(WeiXinConstantUtil.WEIXIN_LOG_SEPARATOR);
        builder.append(requestMessage.getScale()).append(WeiXinConstantUtil.WEIXIN_LOG_SEPARATOR);
        builder.append(requestMessage.getLabel());
        log.setContent(builder.toString());
        addReplyLog(log);
        return handlerReply(requestMessage, WeiXinConstantUtil.WEIXIN_DEFAULT_KEY);
    }

    /**
     * 链接消息处理
     */
    private WeiXinResponse dealMessage(LinkNormalRequestMessage requestMessage) {

        // 记录日志
        WxReplyLog log = wrapWeixinReplyLog(requestMessage);
        StringBuilder builder = new StringBuilder();
        builder.append(requestMessage.getTitle()).append(WeiXinConstantUtil.WEIXIN_LOG_SEPARATOR);
        builder.append(requestMessage.getDescription()).append(WeiXinConstantUtil.WEIXIN_LOG_SEPARATOR);
        builder.append(requestMessage.getUrl());
        log.setContent(builder.toString());
        addReplyLog(log);

        return handlerReply(requestMessage, WeiXinConstantUtil.WEIXIN_DEFAULT_KEY);
    }

    private void addReplyLog(final WxReplyLog entity) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    wxReplyLogBiz.insert(entity);
                } catch (Exception e) {
                    logger.error("添加微信日志异常：" + e.getMessage(), e);
                }
            }
        });
    }

    private WxReplyLog wrapWeixinReplyLog(WeiXinRequest requestMessage) {
        WxReplyLog log = new WxReplyLog();
        log.setAppId(requestMessage.getAppId());
        log.setOpenId(requestMessage.getFromUserName());
        log.setType(requestMessage.getMsgType());
        log.setCreateTime(new Date());
        return log;
    }

}
