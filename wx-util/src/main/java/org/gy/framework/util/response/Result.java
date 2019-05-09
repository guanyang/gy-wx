package org.gy.framework.util.response;

import java.util.HashMap;
import java.util.Map;

/**
 * 用于返回业务处理结果<br>
 * 
 * @author 管阳
 */
public class Result {

    public static final String  SUCCESS_CODE    = ReturnCode.SUCCESS.getCode();

    public static final String  SUCCESS_MESSAGE = ReturnCode.SUCCESS.getMessage();

    /**
     * 返回码，0表示成功，其他表示失败
     */
    private String              code            = SUCCESS_CODE;

    /**
     * 返回消息
     */
    private String              message         = SUCCESS_MESSAGE;

    /**
     * 返回数据
     */
    private Map<String, Object> data;

    /**
     * 是否成功判断
     */
    public boolean isSuccess() {
        return SUCCESS_CODE.equals(this.code);
    }

    public void wrapResultCode(ReturnCode returnCode,
                               Object... placeholder) {
        this.setCode(returnCode.getCode());
        this.setMessage(returnCode.buildMessage(placeholder));
    }

    public Result addObject(String attributeName,
                            Object attributeValue) {
        buildDataMap().put(attributeName, attributeValue);
        return this;
    }

    public Result addAllObjects(Map<String, Object> map) {
        buildDataMap().putAll(map);
        return this;
    }

    public Map<String, Object> buildDataMap() {
        if (this.data == null) {
            this.data = new HashMap<>();
        }
        return this.data;
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

    /**
     * 获取 data
     * 
     * @return data data
     */
    public Map<String, Object> getData() {
        return data;
    }

    /**
     * 设置 data
     * 
     * @param data data
     */
    public void setData(Map<String, Object> data) {
        this.data = data;
    }

}
