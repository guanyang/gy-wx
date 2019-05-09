package org.gy.framework.util;

import java.io.Serializable;

/**
 * 统一Json响应<br>
 * 
 */
public final class ResponseVO<T> implements Serializable {

    private static final long serialVersionUID = -7199257293894269879L;

    /**
     * 是否成功，true成功，fasle失败
     */
    private boolean           success          = false;
    /**
     * 信息提示
     */
    private String            message;
    /**
     * 返回结果
     */
    private T                 result;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

}
