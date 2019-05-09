/**
 * Copyright (C), 2011-2016, org.gy.sample
 * ProjectName:	cpx-util
 * FileName: Template.java
 *
 * @Author gy
 * @Date 2016年8月21日下午10:12:10
 */
package org.gy.framework.util.weixin.message.json.template;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * 功能描述：模板实体
 * 
 * @Author gy
 * @Date 2016年8月21日下午10:12:10
 */
public class Template {

    /**
     * 消息接收方
     */
    @JsonProperty(value = "touser")
    private String                     toUser;
    /**
     * 模板id
     */
    @JsonProperty(value = "template_id")
    private String                     templateId;
    /**
     * 模板消息详情链接
     */
    @JsonProperty(value = "url")
    private String                     url;

    /**
     * 详细内容
     */
    @JsonProperty(value = "data")
    private Map<String, TemplateParam> data;

    /**
     * 获取 toUser
     * 
     * @return toUser toUser
     */
    public String getToUser() {
        return toUser;
    }

    /**
     * 设置 toUser
     * 
     * @param toUser toUser
     */
    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    /**
     * 获取 templateId
     * 
     * @return templateId templateId
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * 设置 templateId
     * 
     * @param templateId templateId
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    /**
     * 获取 url
     * 
     * @return url url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置 url
     * 
     * @param url url
     */
    public void setUrl(String url) {
        this.url = url;
    }


    /**
     * 获取 data
     * 
     * @return data data
     */
    public Map<String, TemplateParam> getData() {
        return data;
    }

    /**
     * 设置 data
     * 
     * @param data data
     */
    public void setData(Map<String, TemplateParam> data) {
        this.data = data;
    }

}
