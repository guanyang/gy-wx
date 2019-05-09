package org.gy.framework.util.weixin.api.shorturl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gy.framework.util.json.JacksonMapper;
import org.gy.framework.util.weixin.api.MethodType;
import org.gy.framework.util.weixin.api.shorturl.model.ShortUrlRequest;
import org.gy.framework.util.weixin.api.shorturl.model.ShortUrlResponse;
import org.gy.framework.util.weixin.util.WeiXinUtil;

public class ShortUrlService {

    private static final Logger logger      = LoggerFactory.getLogger(ShortUrlService.class);

    public static final String  URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/shorturl?access_token={0}";

    private ShortUrlService() {
    }

    /**
     * 功能描述: 获取JSAPI票据
     * 
     * @return
     */
    public static ShortUrlResponse execute(String accessToken,
                                           ShortUrlRequest param) {
        if (StringUtils.isBlank(accessToken) || param == null) {
            logger.error("短连接参数错误：accessToken={},ShortUrlRequest={}", accessToken, JacksonMapper.beanToJson(param));
            return null;
        }
        String url = WeiXinUtil.wrapApiUrl(URL_PATTERN, accessToken);
        return WeiXinUtil.execute(url, MethodType.POST, JacksonMapper.beanToJson(param), ShortUrlResponse.class);
    }

    public static void main(String[] args) {
        String token = "NR9dmuS7japrgDTehfy5LOqIQSlDkYBbeO30Ya4E2M616XT_eEbLP1Nl3cNITLaMJN-lOjAzpIfS9zHDf3QwgDrfn0t57qgs5kxNDYC251UeT59xnWlxyBsvm-W-TJ9ZPGLhAIAFWB";
        ShortUrlRequest param = new ShortUrlRequest();
        param.setLongUrl("http://www.baidu.com/");
        ShortUrlResponse response = execute(token, param);
        System.out.println(response.getShortUrl());
    }

}
