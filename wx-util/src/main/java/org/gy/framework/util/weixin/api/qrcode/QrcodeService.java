package org.gy.framework.util.weixin.api.qrcode;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.gy.framework.util.json.JacksonMapper;
import org.gy.framework.util.weixin.api.MethodType;
import org.gy.framework.util.weixin.api.qrcode.model.QrActionInfo;
import org.gy.framework.util.weixin.api.qrcode.model.QrScene;
import org.gy.framework.util.weixin.api.qrcode.model.QrcodeRequest;
import org.gy.framework.util.weixin.api.qrcode.model.QrcodeResponse;
import org.gy.framework.util.weixin.util.WeiXinUtil;

public class QrcodeService {

    private static final Logger logger      = LoggerFactory.getLogger(QrcodeService.class);

    public static final String  URL_PATTERN = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token={0}";

    private QrcodeService() {
    }

    /**
     * 功能描述: 获取永久二维码
     * 
     * @return
     */
    public static QrcodeResponse execute(String accessToken,
                                         String sceneStr) {
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(sceneStr)) {
            logger.error("[QrcodeService]参数错误：accessToken={},sceneStr={}", accessToken, sceneStr);
            QrcodeResponse response = new QrcodeResponse();
            response.setErrcode("-1");
            response.setErrmsg("必填参数为空错误");
            return response;
        }
        QrcodeRequest param = new QrcodeRequest();
        param.setActionName("QR_LIMIT_STR_SCENE");
        QrActionInfo info = new QrActionInfo();
        param.setQrActionInfo(info);
        QrScene scene = new QrScene();
        info.setQrScene(scene);
        scene.setSceneStr(sceneStr);
        String url = WeiXinUtil.wrapApiUrl(URL_PATTERN, accessToken);
        return WeiXinUtil.execute(url, MethodType.POST, JacksonMapper.beanToJson(param), QrcodeResponse.class);
    }

    public static void main(String[] args) {
        String token = "HoO2Hlmb4BQtGoHKHV6kfDMu8BDDleyrh-DQHD-JVhxSEUahoChxgfVL-2TWw9WR_ydfCJyaHZrW9oyNgNCiz7ZsfharJ_WEInlX187IxSsNAMeAFAQDP";
        String sceneStr = "9999";
        QrcodeResponse response = execute(token, sceneStr);
        System.out.println(JacksonMapper.beanToJson(response));
    }
}
