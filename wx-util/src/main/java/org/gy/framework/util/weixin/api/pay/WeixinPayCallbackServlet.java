package org.gy.framework.util.weixin.api.pay;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.SortedMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import org.gy.framework.util.jedis.RedisClient;
import org.gy.framework.util.response.Result;
import org.gy.framework.util.weixin.api.pay.model.WeixinPayCallbackRequest;
import org.gy.framework.util.weixin.api.pay.model.WeixinPayCallbackResponse;
import org.gy.framework.util.weixin.util.WeiXinUtil;

public class WeixinPayCallbackServlet extends HttpServlet {

    private static final long        serialVersionUID = 4499999673265708642L;

    private static final Logger      LOGGER           = LoggerFactory.getLogger(WeixinPayCallbackServlet.class);

    public static final String       DEFAULT_ENCODING = "UTF-8";

    @Autowired
    private RedisClient              redisClient;

    @Autowired
    @Qualifier("apiSupplierRechargeBiz")
    private WeixinPayCallbackService weixinPayCallbackService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
    }

    @Override
    protected void service(HttpServletRequest request,
                           HttpServletResponse response) throws ServletException, IOException {
        String xml = null;
        InputStream is = null;
        response.setContentType("text/xml;charset=utf-8");
        try {
            is = request.getInputStream();
            xml = IOUtils.toString(is, "UTF-8");
        } catch (IOException e) {
            LOGGER.error("读取支付回调数据错误：" + e.getMessage(), e);
        } finally {
            IOUtils.closeQuietly(is);
        }
        if (StringUtils.isBlank(xml)) {
            callbackResponse(response, WeixinPay.FAIL, "读取支付报文失败");
            return;
        }
        LOGGER.info("支付通知报文：" + xml);
        try {
            // 读取报文成功
            WeixinPayCallbackRequest param = WeiXinUtil.xml2bean(xml, WeixinPayCallbackRequest.class);

            if (!isWeixinSign(param)) {
                // 签名验证没有通过
                LOGGER.error("支付回调通知签名验证失败：" + xml);
                callbackResponse(response, WeixinPay.FAIL, "签名失败");
                return;
            }
            // 如果交易标识不成功，直接返回
            if (!WeixinPay.SUCCESS.equals(param.getResult_code())) {
                LOGGER.error("支付回调通知交易失败：" + xml);
                callbackResponse(response, WeixinPay.FAIL, "交易失败");
                return;
            }
            Result result = weixinPayCallbackService.processSuccessRequest(param);
            String code = WeixinPay.SUCCESS;
            String message = WeixinPay.SUCCESS;
            if (!result.isSuccess()) {

                LOGGER.error("支付回调通知处理失败：param={},code={},message={}", xml, result.getCode(), result.getMessage());
                code = WeixinPay.FAIL;
                message = result.getCode() + "-" + result.getMessage();
            }
            callbackResponse(response, code, message);
            return;
        } catch (Exception e) {
            LOGGER.error("支付通知处理异常：" + xml, e);
            callbackResponse(response, WeixinPay.FAIL, "支付通知处理异常");
            return;
        }

    }

    private void callbackResponse(HttpServletResponse response,
                                  String code,
                                  String message) throws IOException {
        WeixinPayCallbackResponse callback = new WeixinPayCallbackResponse();
        callback.setReturn_code(code);
        callback.setReturn_msg(message);
        String resXml = WeiXinUtil.bean2xml(callback);

        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes(DEFAULT_ENCODING));
        out.flush();
        out.close();
    }

    /**
     * 校验微信返回的sign签名是否正确
     * 
     * @param weixinSign
     * @param weixinpayRequestBody
     * @return
     */
    private boolean isWeixinSign(WeixinPayCallbackRequest param) {
        if (param != null) {
            String weixinSign = param.getSign();
            SortedMap<String, Object> parameters = WeiXinUtil.convertBean(param);
            String localSign = WeixinPay.createSign(parameters);
            if (localSign.equals(weixinSign)) {
                return true;
            }
        }
        return false;
    }
}
