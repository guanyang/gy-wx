package org.gy.framework.util.weixin.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.gy.framework.util.httpclient.HttpClientUtil;
import org.gy.framework.util.json.JacksonMapper;
import org.gy.framework.util.weixin.api.MethodType;
import org.gy.framework.util.weixin.exception.WeiXinException;
import org.gy.framework.util.weixin.message.xml.request.LinkNormalRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.LocationNormalRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.MenuClickEventRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.MenuViewEventRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.ScanEventRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.SubscribeEventRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.TemplateEventRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.TextNormalRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.UnSubscribeEventRequestMessage;
import org.gy.framework.util.weixin.message.xml.request.WeiXinRequest;
import org.gy.framework.util.weixin.message.xml.response.WeiXinResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;

public class WeiXinUtil {

    private static final Logger                                      logger            = LoggerFactory.getLogger(WeiXinUtil.class);

    private static final Map<String, Class<? extends WeiXinRequest>> MESSAGE_TYPE_MAP  = new HashMap<>();

    public static final String                                       MSG_TYPE_KEY      = "MsgType";

    public static final String                                       EVENT_TYPE_KEY    = "Event";

    private static final XStream                                     objectToXml;

    private static final Map<String, XStream>                        xmlToObjectMap    = new HashMap<>();

    private static final String                                      DEFAULT_XML_ALIAS = "xml";

    public static final String                                       PAY_SIGN_PARAM    = "sign";

    public static final String                                       PAY_KEY_PARAM     = "key";

    private static Validator                                         validator         = Validation.buildDefaultValidatorFactory().getValidator();

    static {
        objectToXml = new XStream(new SimpleXpp3Driver());
        objectToXml.autodetectAnnotations(true);

        // 初始化消息类型
        MESSAGE_TYPE_MAP.put(WeiXinConstantUtil.MESSAGE_TYPE_TEXT, TextNormalRequestMessage.class);
        MESSAGE_TYPE_MAP.put(WeiXinConstantUtil.MESSAGE_TYPE_LOCATION, LocationNormalRequestMessage.class);
        MESSAGE_TYPE_MAP.put(WeiXinConstantUtil.MESSAGE_TYPE_LINK, LinkNormalRequestMessage.class);
        MESSAGE_TYPE_MAP.put(WeiXinConstantUtil.EVENT_TYPE_SUBSCRIBE, SubscribeEventRequestMessage.class);
        MESSAGE_TYPE_MAP.put(WeiXinConstantUtil.EVENT_TYPE_UNSUBSCRIBE, UnSubscribeEventRequestMessage.class);
        MESSAGE_TYPE_MAP.put(WeiXinConstantUtil.EVENT_TYPE_SCAN, ScanEventRequestMessage.class);
        MESSAGE_TYPE_MAP.put(WeiXinConstantUtil.EVENT_TYPE_CLICK, MenuClickEventRequestMessage.class);
        MESSAGE_TYPE_MAP.put(WeiXinConstantUtil.EVENT_TYPE_VIEW, MenuViewEventRequestMessage.class);
        MESSAGE_TYPE_MAP.put(WeiXinConstantUtil.EVENT_TYPE_TEMPLATESENDJOBFINISH, TemplateEventRequestMessage.class);

    }

    private WeiXinUtil() {

    }

    /**
     * 
     * 功能描述:
     * 
     * @return WeiXinMessage
     */
    public static WeiXinRequest parsingWeiXinMessage(String xml) {
        Map<String, String> messageMap = null;
        try {
            messageMap = parseXml(xml);
        } catch (Exception e) {
            throw new WeiXinException("解析xml异常：" + e.getMessage(), e);
        }
        String msgType = messageMap.get(MSG_TYPE_KEY);
        String eventType = messageMap.get(EVENT_TYPE_KEY);
        Class<? extends WeiXinRequest> clazz;
        if (WeiXinConstantUtil.MESSAGE_TYPE_EVENT.equals(msgType)) {
            clazz = MESSAGE_TYPE_MAP.get(eventType);
        } else {
            clazz = MESSAGE_TYPE_MAP.get(msgType);
        }
        if (clazz == null) {
            logger.warn("未实现的消息类型：" + JacksonMapper.beanToJson(messageMap));// 不需要抛异常，只处理实现的消息类型，有些消息类型不需要实现
            return null;
        }
        WeiXinRequest message = null;
        try {
            message = xml2bean(xml, clazz);
        } catch (Exception e) {
            throw new WeiXinException("xml转换为bean异常：" + e.getMessage(), e);
        }
        return message;
    }

    /**
     * 解析微信发来的请求（XML）
     * 
     * @param request
     * @return
     * @throws DocumentException
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> parseXml(String xml) throws DocumentException {
        // 将解析结果存储在HashMap中
        Map<String, String> map = new HashMap<>();

        Document document = DocumentHelper.parseText(xml);
        Element root = document.getRootElement();
        // 得到根元素的所有子节点
        List<Element> elementList = root.elements();

        // 遍历所有子节点
        for (Element e : elementList) {
            map.put(e.getName(), e.getText());
        }

        return map;
    }

    /**
     * 
     * 功能描述: 生成响应消息
     * 
     * @param requestMessage 用于交换from和to
     * @return WeiXinMessage
     */
    public static WeiXinResponse generateResponseMessage(WeiXinRequest requestMessage) {
        WeiXinResponse responseMessage = new WeiXinResponse();
        return packageResponseMessage(requestMessage, responseMessage);
    }

    /**
     * 
     * 功能描述: 生成响应消息
     * 
     * @param requestMessage 用于交换from和to
     * @return WeiXinMessage
     */
    public static WeiXinResponse changeFromUserToUser(WeiXinRequest requestMessage,
                                                      WeiXinResponse responseMessage) {

        responseMessage.setFromUserName(requestMessage.getToUserName());
        responseMessage.setToUserName(requestMessage.getFromUserName());

        return responseMessage;
    }

    /**
     * 
     * 功能描述: 打包消息--交换from to、添加创建时间
     * 
     */
    public static WeiXinResponse packageResponseMessage(WeiXinRequest request,
                                                        WeiXinResponse response) {

        changeFromUserToUser(request, response);
        response.setCreateTime(System.currentTimeMillis());
        return response;
    }

    /**
     * 
     * 功能描述: bean to xml 使用 xstream实现
     * 
     */
    public static String bean2xml(Object obj) {
        return objectToXml.toXML(obj);

    }

    /**
     * 
     * 功能描述: xml转换为bean
     * 
     */
    @SuppressWarnings("unchecked")
    public static <T> T xml2bean(String xml,
                                 Class<T> clazz) {
        XStream xStream = checkDuplicateAliasClassXStream(clazz);
        xStream.processAnnotations(clazz);
        return (T) xStream.fromXML(xml);
    }

    private static XStream checkDuplicateAliasClassXStream(Class<?> clazz) {
        XStream xStream;
        XStreamAlias classAlias = clazz.getAnnotation(XStreamAlias.class);
        if (classAlias != null && DEFAULT_XML_ALIAS.equals(classAlias.value())) {
            xStream = xmlToObjectMap.get(clazz.getName());
            if (xStream == null) {
                synchronized (xmlToObjectMap) {
                    if ((xStream = xmlToObjectMap.get(clazz.getName())) == null) {
                        xStream = new XStream();
                        xStream.autodetectAnnotations(true);
                        xmlToObjectMap.put(clazz.getName(), xStream);
                    }
                }
            }
        } else {
            xStream = new XStream();
            xStream.alias(DEFAULT_XML_ALIAS, clazz);
        }
        return xStream;
    }

    public static Properties loadProperties(String location) {
        Properties properties = new Properties();
        try (InputStream is = WeiXinUtil.class.getClassLoader().getResourceAsStream(location)) {
            properties.load(is);
        } catch (IOException e) {
            throw new WeiXinException("加载配置文件异常：" + e.getMessage(), e);
        }
        return properties;
    }

    /**
     * 功能描述: 计算采用utf-8编码方式时字符串所占字节数
     * 
     * @param content
     * @return
     * @version 2.0.0
     * @author guanyang
     */
    public static int getByteSize(String content) {
        int size = 0;
        if (null != content) {
            // 汉字采用utf-8编码时占3个字节
            try {
                size = content.getBytes("utf-8").length;
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return size;
    }

    /**
     * 功能描述:执行请求
     * 
     * @param url 请求地址
     * @param type 请求类型，支持get，post
     * @param content 请求body报文，没有传空值
     * @param responseType 响应结果类型
     * @return
     */
    public static <T> T execute(String url,
                                MethodType type,
                                String content,
                                Class<T> responseType) {
        String result = execute(url, type, content);
        return JacksonMapper.jsonToBean(result, responseType);
    }

    /**
     * 功能描述:执行请求
     * 
     * @param url 请求地址
     * @param type 请求类型，支持get，post
     * @param content 请求body报文，没有传空值
     * @return
     */
    public static String execute(String url,
                                 MethodType type,
                                 String content) {
        logger.info("执行请求参数快照：url={},type={},content={}", url, type, content);
        if (MethodType.POST.equals(type)) {
            return HttpClientUtil.postBody(url, content, null);
        } else if (MethodType.GET.equals(type)) {
            return HttpClientUtil.get(url, null);
        } else {
            throw new WeiXinException("unknown type：" + type);
        }
    }

    /**
     * 功能描述: 构建请求url
     * 
     * @param pattern url模板
     * @param arguments 参数组
     * @return
     */
    public static String wrapApiUrl(String pattern,
                                    Object... arguments) {
        return MessageFormat.format(pattern, arguments);
    }

    /**
     * 功能描述:将一个 JavaBean对象转化为一个 SortedMap
     * 
     * @param bean
     * @return
     */
    public static SortedMap<String, Object> convertBean(Object bean) {
        SortedMap<String, Object> returnMap = new TreeMap<>();
        BeanInfo beanInfo;
        try {
            @SuppressWarnings("rawtypes")
            Class type = bean.getClass();
            beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!"class".equals(propertyName)) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);

                    returnMap.put(propertyName, result);
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return returnMap;
    }

    /**
     * 功能描述:创建微信支付签名，排除sign参数
     * 
     * @param parameters 参数map
     * @param apiKey 安全密钥
     * @return
     */
    public static String createSign(SortedMap<String, Object> parameters,
                                    String apiKey) {
        return createSign(parameters, apiKey, new String[] {
            PAY_SIGN_PARAM
        });

    }

    /**
     * 功能描述:创建微信支付签名
     * 
     * @param parameters 参数map
     * @param apiKey 安全密钥
     * @param ignoreParams 需要忽略签名的参数
     * @return
     */
    private static String createSign(SortedMap<String, Object> parameters,
                                     String apiKey,
                                     String[] ignoreParams) {
        StringBuilder sb = new StringBuilder();
        // 所有参与传参的参数按照accsii排序（升序）
        for (Entry<String, Object> entry : parameters.entrySet()) {
            String k = entry.getKey();
            Object v = entry.getValue();
            if (null != v && !"".equals(v) && !StringUtils.containsAny(k, ignoreParams)) {
                sb.append(k).append("=").append(v).append("&");
            }
        }
        sb.append(PAY_KEY_PARAM).append("=").append(apiKey);// API安全密钥
        return DigestUtils.md5Hex(sb.toString()).toUpperCase();
    }

    /**
     * 功能描述:微信支付请求的xml封装
     * 
     * @param parameters
     * @return
     */
    public static String buildRequestXML(Map<String, Object> parameters) {
        StringBuilder buf = new StringBuilder();
        buf.append("<xml>");
        for (Entry<String, Object> entry : parameters.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();

            if (null != value && !"".equals(value)) {
                buf.append("<").append(key).append(">");

                if (value instanceof String) {
                    buf.append("<![CDATA[");
                }
                buf.append(value);
                if (value instanceof String) {
                    buf.append("]]>");
                }
                buf.append("</").append(key).append(">");
            }

        }
        buf.append("</xml>");
        return buf.toString();
    }

    /**
     * 随机字符串
     * 
     */
    public static String getNonceStr() {
        return RandomStringUtils.randomAlphanumeric(32);
    }

    /**
     * 时间戳，标准北京时间，时区为东八区，自1970年1月1日 0点0分0秒以来的秒数
     * 
     */
    public static String getTimeStamp() {
        return String.valueOf(System.currentTimeMillis() / 1000);
    }

    /**
     * 通过jsapi_ticket生成微信浏览器的签名
     * 
     * @param ticket
     * @param url
     * @param timestamp
     * @param nonce_str
     * @return
     */
    public static String signature(String ticket,
                                   String url,
                                   String timeStamp,
                                   String nonceStr) {
        // 需要做sha1签名的字符串
        StringBuilder sourceStr = new StringBuilder("jsapi_ticket=").append(ticket).append("&noncestr=").append(nonceStr).append("&timestamp=").append(timeStamp).append("&url=").append(url);
        return DigestUtils.sha1Hex(sourceStr.toString());
    }

    /**
     * 功能描述:JSR303参数校验
     * 
     * @param param
     * @return
     */
    public static ConstraintViolation<Object> validateParam(Object param) {
        if (param != null) {
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(param);
            return CollectionUtils.isNotEmpty(constraintViolations) ? constraintViolations.iterator().next() : null;
        }
        return null;
    }

}
