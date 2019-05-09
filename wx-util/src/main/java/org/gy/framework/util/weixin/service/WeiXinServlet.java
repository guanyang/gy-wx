package org.gy.framework.util.weixin.service;

import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.gy.framework.util.weixin.message.xml.request.WeiXinRequest;
import org.gy.framework.util.weixin.util.SignUtil;
import org.gy.framework.util.weixin.util.WeiXinUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * 功能描述：请求处理类
 * 
 */
public class WeiXinServlet extends HttpServlet {

    private static final Logger         LOGGER                = LoggerFactory.getLogger(WeiXinServlet.class);

    private static final long           serialVersionUID      = 4133082469608256850L;

    public static final String          DEFAULT_ENCODING      = "UTF-8";

    /**
     * 微信推荐异常回复
     */
    public static final String          DEFAULT_WEIXIN_RETURN = "success";

    public static final String          APPID_PARAM           = "appId";

    private transient WeiXinCoreService weiXinCoreService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(config.getServletContext());
        weiXinCoreService = applicationContext.getBean(WeiXinCoreService.class);
    }

    /**
     * 确认请求来自微信服务器
     */
    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) {
        // 获取appId
        String appId = request.getParameter(APPID_PARAM);
        if (StringUtils.isBlank(appId)) {
            LOGGER.error("wx execute param appid is null");
            return;
        }
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");
        try (PrintWriter out = response.getWriter()) {
            // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
            String token = weiXinCoreService.getValidateToken(appId);
            if (SignUtil.checkSignature(token, signature, timestamp, nonce)) {
                out.print(echostr);
            }
        } catch (Exception e) {
            LOGGER.error("wx check exception:" + e.getMessage(), e);
        }

    }

    /**
     * 处理微信服务器发来的消息<br>
     * 
     */
    @Override
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) {
        try (InputStream is = request.getInputStream(); PrintWriter out = response.getWriter()) {
            // 获取appid
            String appId = request.getParameter(APPID_PARAM);
            if (StringUtils.isBlank(appId)) {
                LOGGER.error("wx execute param appid is null");
                out.print(DEFAULT_WEIXIN_RETURN);// 默认回复
                return;
            }
            String xml = IOUtils.toString(is, DEFAULT_ENCODING);
            String respMessage = null;
            if (StringUtils.isNotBlank(xml)) {
                WeiXinRequest message = WeiXinUtil.parsingWeiXinMessage(xml);
                // 消息类型未实现时，返回空值，不需要处理，只处理非空值
                if (message != null) {
                    // 调用核心业务类接收消息、处理消息
                    message.setAppId(appId);
                    respMessage = weiXinCoreService.processRequest(message);
                }
            }
            if (respMessage == null) {
                respMessage = DEFAULT_WEIXIN_RETURN;
            }
            // 响应消息
            out.print(respMessage);
        } catch (Exception e) {
            LOGGER.error("wx execute exception:" + e.getMessage(), e);
        }
    }
}
