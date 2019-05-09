package org.gy.framework.util.weixin.api.pay;

import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import org.gy.framework.util.httpclient.HttpClientUtil;
import org.gy.framework.util.weixin.api.pay.model.WeixinOrderRequest;
import org.gy.framework.util.weixin.api.pay.model.WeixinOrderResponse;
import org.gy.framework.util.weixin.api.pay.model.WeixinPayJsApiRequest;
import org.gy.framework.util.weixin.api.pay.model.WeixinPayJsApiResponse;
import org.gy.framework.util.weixin.api.pay.model.WeixinRedPackRequest;
import org.gy.framework.util.weixin.api.pay.model.WeixinRedPackResponse;
import org.gy.framework.util.weixin.config.WeiXinConfig;
import org.gy.framework.util.weixin.util.WeiXinUtil;
import org.gy.framework.util.weixin.util.WeixinPaySSLUtil;

public class WeixinPay {

    private static Validator    validator               = Validation.buildDefaultValidatorFactory().getValidator();
    // 公众号appid
    public static final String  PAY_APP_ID              = WeiXinConfig.getValue("wx.pay.appid");
    // 商户号
    public static final String  PAY_MCH_ID              = WeiXinConfig.getValue("wx.pay.mchid");
    // API密钥
    public static final String  PAY_API_KEY             = WeiXinConfig.getValue("wx.pay.api.key");
    // 微信统一下单
    public static final String  PAY_UNIFIEDORDER        = WeiXinConfig.getValue("wx.pay.unifiedorder");
    // 本系统发起微信请求页面
    public static final String  PAY_REQUEST_PAGE        = WeiXinConfig.getValue("wx.pay.requestPage");
    // 本系统微信支付通知路径
    public static final String  PAY_NOTIFY_URL          = WeiXinConfig.getValue("wx.pay.notifyUrl");
    // 微信红包URL
    public static final String  SEND_RED_PACK_URL       = WeiXinConfig.getValue("wx.pay.sendredpack");

    private static final String SIGN_TYPE_MD5           = "MD5";
    // 扫描支付
    public static final String  SCAN_CODE_TRADE_CHANNEL = "NATIVE";
    // 公众号支付
    public static final String  WEI_XIN_TRADE_CHANNEL   = "JSAPI";
    // 成功标志
    public static final String  SUCCESS                 = "SUCCESS";
    // 失败标志
    public static final String  FAIL                    = "FAIL";

    private WeixinPay() {

    }

    /**
     * 功能描述:构建微信JSAPI支付参数
     * 
     * @param param
     * @return
     */
    public static WeixinPayJsApiResponse wrapWeixinJsApiResponse(WeixinPayJsApiRequest param) {

        String finalTimeStamp = WeiXinUtil.getTimeStamp();
        String finalNonceStr = WeiXinUtil.getNonceStr();
        String finalPackage = "prepay_id=" + param.getPrepayId();
        String signature = WeiXinUtil.signature(param.getJsApiTicket(), param.getUrl(), finalTimeStamp, finalNonceStr);

        SortedMap<String, Object> finalSortedMap = new TreeMap<>();
        finalSortedMap.put("appId", PAY_APP_ID);
        finalSortedMap.put("timeStamp", finalTimeStamp);
        finalSortedMap.put("nonceStr", finalNonceStr);
        finalSortedMap.put("package", finalPackage);
        finalSortedMap.put("signType", SIGN_TYPE_MD5);
        String finalSign = createSign(finalSortedMap);

        WeixinPayJsApiResponse response = new WeixinPayJsApiResponse();
        response.setPaySign(finalSign);
        response.setAppId(PAY_APP_ID);
        response.setTimeStamp(finalTimeStamp);
        response.setNonceStr(finalNonceStr);
        response.setPackageBody(finalPackage);
        response.setSignType(SIGN_TYPE_MD5);
        response.setSignature(signature);
        response.setCode_url(param.getCodeUrl());
        response.setTrade_type(param.getTradeType());// 扫描 或 微信
        response.setPrepay_id(param.getPrepayId());
        return response;

    }

    /**
     * 功能描述:微信统一下单
     * 
     * @param param
     * @return
     */
    public static WeixinOrderResponse createOrder(WeixinOrderRequest param) {
        WeixinOrderResponse response = new WeixinOrderResponse();
        if (param == null) {
            response.setReturn_code(WeixinPay.FAIL);
            response.setReturn_msg("参数对象为空");
            return response;
        }
        // 入参校验
        ConstraintViolation<Object> violation = validatePayParam(param);
        if (violation != null) {
            response.setReturn_code(WeixinPay.FAIL);
            response.setReturn_msg(violation.getMessage());
            return response;
        }
        SortedMap<String, Object> parameters = WeiXinUtil.convertBean(param);
        String sign = createSign(parameters);
        parameters.put(WeiXinUtil.PAY_SIGN_PARAM, sign);
        String requestXML = WeiXinUtil.buildRequestXML(parameters);
        String rst = HttpClientUtil.postBody(PAY_UNIFIEDORDER, requestXML, null);
        if (StringUtils.isBlank(rst)) {
            response.setReturn_code(WeixinPay.FAIL);
            response.setReturn_msg("统一下单接口返回为空");
            return response;
        }
        response = WeiXinUtil.xml2bean(rst, WeixinOrderResponse.class);
        response.setRequestXml(requestXML);// 记录入参报文
        response.setResponseXml(rst);// 记录响应报文
        return response;

    }

    /**
     * 功能描述:发放普通红包
     * 
     * @param param
     * @return
     */
    public static WeixinRedPackResponse sendRedPack(WeixinRedPackRequest param) {

        WeixinRedPackResponse response = new WeixinRedPackResponse();

        SortedMap<String, Object> parameters = WeiXinUtil.convertBean(param);
        String sign = createSign(parameters);
        parameters.put(WeiXinUtil.PAY_SIGN_PARAM, sign);
        String requestXML = WeiXinUtil.buildRequestXML(parameters);

        String result = WeixinPaySSLUtil.postBody(SEND_RED_PACK_URL, requestXML, null);
        if (StringUtils.isBlank(result)) {
            response.setReturn_code(WeixinPay.FAIL);
            response.setReturn_msg("微信普通红包接口返回为空");
            return response;
        }
        response = WeiXinUtil.xml2bean(result, WeixinRedPackResponse.class);
        response.setRequestXml(requestXML);// 记录入参报文
        response.setResponseXml(result);// 记录响应报文

        return response;
    }

    /**
     * 功能描述:创建
     * 
     * @param parameters
     * @return
     */
    public static String createSign(SortedMap<String, Object> parameters) {
        return WeiXinUtil.createSign(parameters, PAY_API_KEY);
    }

    /**
     * 功能描述:微信统一下单参数校验
     * 
     * @param param
     * @return
     */
    public static ConstraintViolation<Object> validatePayParam(Object param) {
        if (param != null) {
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(param);
            return CollectionUtils.isNotEmpty(constraintViolations) ? constraintViolations.iterator().next() : null;
        }
        return null;
    }

}
