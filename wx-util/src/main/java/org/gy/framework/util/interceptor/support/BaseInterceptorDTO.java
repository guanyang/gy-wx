package org.gy.framework.util.interceptor.support;

/**
 * 功能描述：基类
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class BaseInterceptorDTO extends CommonInterceptorDTO {

    private static final long  serialVersionUID = 2230553030766621644L;

    public static final String SUCCESS_CODE     = CodeType.SUCCESS_CODE.getCode();

    /**
     * 返回码，0表示成功，其他表示失败
     */
    private String             code             = SUCCESS_CODE;

    /**
     * 返回消息
     */
    private String             message;

    /**
     * 是否成功判断
     */
    public boolean isSuccess() {
        return SUCCESS_CODE.equals(this.code);
    }

    /**
     * 获取返回码，0表示成功，其他表示失败
     * 
     * @return code 返回码，0表示成功，其他表示失败
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置返回码，0表示成功，其他表示失败
     * 
     * @param code 返回码，0表示成功，其他表示失败
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 获取返回消息
     * 
     * @return message 返回消息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置返回消息
     * 
     * @param message 返回消息
     */
    public void setMessage(String message) {
        this.message = message;
    }

}
