/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: TemplateParam.java
 *
 * @Author gy
 * @Date 2016年8月21日下午10:12:35
 */
package org.gy.framework.util.weixin.message.json.template;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 功能描述：模板参数实体
 * 
 * @Author gy
 * @Date 2016年8月21日下午10:12:35
 */
public class TemplateParam {

    /**
     * 参数值
     */
    @JsonProperty(value = "value")
    private String value;
    /**
     * 颜色
     */
    @JsonProperty(value = "color")
    private String color = "#48b269";

    public TemplateParam(String value, String color) {
        this.value = value;
        this.color = color;
    }

    public TemplateParam(String value) {
        this.value = value;
    }

    /**
     * 功能描述：无参构造方法，必须存在，否则反序列化报错
     * 
     * @Author gy
     */
    public TemplateParam() {
        //
    }

    /**
     * 获取 value
     * 
     * @return value value
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置 value
     * 
     * @param value value
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取 color
     * 
     * @return color color
     */
    public String getColor() {
        return color;
    }

    /**
     * 设置 color
     * 
     * @param color color
     */
    public void setColor(String color) {
        this.color = color;
    }

}
