package org.gy.framework.util.weixin.api.qrcode.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class QrcodeRequest {

    /**
     * 二维码类型，QR_SCENE为临时的整型参数值，QR_STR_SCENE为临时的字符串参数值，QR_LIMIT_SCENE为永久的整型参数值，QR_LIMIT_STR_SCENE为永久的字符串参数值
     */
    @JsonProperty(value = "action_name")
    private String       actionName = "QR_LIMIT_STR_SCENE";

    /**
     * 二维码详细信息
     */
    @JsonProperty(value = "action_info")
    private QrActionInfo qrActionInfo;

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public QrActionInfo getQrActionInfo() {
        return qrActionInfo;
    }

    public void setQrActionInfo(QrActionInfo qrActionInfo) {
        this.qrActionInfo = qrActionInfo;
    }

}
