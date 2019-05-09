package org.gy.framework.util.weixin.service;

import org.gy.framework.util.weixin.message.xml.request.WeiXinRequest;

/**
 * 功能描述：核心业务处理接口
 * 
 */
public interface WeiXinCoreService {

    /**
     * 核心业务处理
     */
    String processRequest(WeiXinRequest message);

    /**
     * 获取验证token
     */
    String getValidateToken(String appId);

}
