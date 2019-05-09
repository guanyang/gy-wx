package org.gy.framework.util.interceptor.support;

/**
 * 功能描述：接口统一返回DTO
 * 
 */
public final class ResponseDTO<T> extends BaseInterceptorDTO {

    private static final long serialVersionUID = 8612413429607062775L;

    /**
     * 结果集
     */
    private T                 result;

    /**
     * 获取结果集
     * 
     * @return result 结果集
     */
    public T getResult() {
        return result;
    }

    /**
     * 设置结果集
     * 
     * @param result 结果集
     */
    public void setResult(T result) {
        this.result = result;
    }

}
