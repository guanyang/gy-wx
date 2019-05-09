package org.gy.framework.util.weixin.api.qrcode.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class QrScene {

    /**
     * 场景字符串
     */
    @JsonProperty(value = "scene_str")
    private String sceneStr;

    public String getSceneStr() {
        return sceneStr;
    }

    public void setSceneStr(String sceneStr) {
        this.sceneStr = sceneStr;
    }

}
