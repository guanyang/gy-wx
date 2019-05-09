package org.gy.framework.util.response;

import java.text.MessageFormat;

/**
 * 功能描述：返回码声明
 * <p>
 * <strong>说明：</strong>
 * </p>
 * <ul>
 * <li>根据具体业务定义具体状态码</li>
 * <li>状态码枚举格式：E{状态码}</li>
 * </ul>
 * 
 */
public enum ReturnCode {

    SUCCESS("0", "操作成功"),

    E1001("1001", "用户名或密码不能为空"),

    E1002("1002", "用户名或密码不正确"),

    E1003("1003", "您的账户{0}已被锁定"),

    E1004("1004", "账号{0}登录失败次数过多,锁定1小时！"),

    E9901("9901", "系统繁忙，请稍后重试！"),

    E9902("9902", "网络异常，请稍后重试！"),

    E9998("9998", "参数错误：{0}"),

    E9999("9999", "系统异常，请稍后重试！");

    /**
     * 返回码
     */
    private String code;
    /**
     * 返回消息
     */
    private String message;

    ReturnCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

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
     * @param returnCode
     * @param placeholder
     * @return
     */
    public static String buildMessageWithCode(ReturnCode returnCode,
                                              Object... placeholder) {
        return new StringBuilder("[").append(returnCode.getCode()).append("]").append(buildMessage(returnCode, placeholder)).toString();
    }

    /**
     * 获取格式化消息，不带错误码
     * 
     * @param returnCode
     * @param placeholder
     * @return
     */
    public static String buildMessage(ReturnCode returnCode,
                                      Object... placeholder) {
        return MessageFormat.format(returnCode.getMessage(), placeholder);
    }

    /**
     * 获取返回码
     * 
     * @return code 返回码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取返回消息
     * 
     * @return message 返回消息
     */
    public String getMessage() {
        return message;
    }

}
