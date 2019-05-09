package org.gy.framework.util.weixin.api.qrcode.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class QrActionInfo {

    @JsonProperty(value = "scene")
    private QrScene qrScene;

    public QrScene getQrScene() {
        return qrScene;
    }

    public void setQrScene(QrScene qrScene) {
        this.qrScene = qrScene;
    }

}
