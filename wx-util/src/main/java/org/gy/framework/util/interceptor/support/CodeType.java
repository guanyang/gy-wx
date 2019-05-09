package org.gy.framework.util.interceptor.support;

import java.text.MessageFormat;

/**
 * 功能描述：异常码统一定义
 * 
 * <p>
 * 说明：
 * </p>
 * <ul>
 * <li>每个接口必须维护接口文档</li>
 * <li>枚举格式：E{3位接口序号}{3位错误码序列}</li>
 * <li>code格式：同枚举格式</li>
 * <li>pattern格式：占位符可以定义多个或者不定义，示例：{0}:{1}</li>
 * <li>description格式：简单描述当前枚举功能</li>
 * </ul>
 * 
 */
public enum CodeType {

    SUCCESS_CODE("0", "操作成功", "操作成功枚举"),

    E999001("E999001", "参数错误：{0}", "统一参数校验错误枚举"),

    E999999("E999999", "系统异常：{0}", "统一系统异常枚举");

    /**
     * 标识码
     */
    private String code;
    /**
     * 消息格式
     */
    private String pattern;
    /**
     * 描述
     */
    private String description;

    /**
     * 功能描述: 获取格式化消息，带错误码
     * 
     * @param placeholder
     * @return
     */
    public String buildMessageWithCode(Object... placeholder) {
        return buildMessageWithCode(this, placeholder);
    }

    /**
     * 功能描述: 获取格式化消息，不带错误码
     * 
     * @param placeholder
     * @return
     */
    public String buildMessage(Object... placeholder) {
        return buildMessage(this, placeholder);
    }

    /**
     * 获取格式化消息，带错误码
     * 
     * @param type
     * @param placeholder
     * @return
     */
    public static String buildMessageWithCode(CodeType type,
                                              Object... placeholder) {
        return new StringBuilder("[").append(type.getCode()).append("]").append(buildMessage(type, placeholder)).toString();
    }

    /**
     * 获取格式化消息，不带错误码
     * 
     * @param type
     * @param placeholder
     * @return
     */
    public static String buildMessage(CodeType type,
                                      Object... placeholder) {
        return MessageFormat.format(type.getPattern(), placeholder);
    }

    private CodeType(String code, String pattern, String description) {
        this.code = code;
        this.pattern = pattern;
        this.description = description;
    }

    /**
     * 获取描述
     * 
     * @return description 描述
     */
    public String getDescription() {
        return description;
    }

    /**
     * 获取标识码
     * 
     * @return code 标识码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取消息格式
     * 
     * @return pattern 消息格式
     */
    public String getPattern() {
        return pattern;
    }

}
