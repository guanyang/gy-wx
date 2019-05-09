package org.gy.framework.util.interceptor.support;

/**
 * 功能描述：接口封装异常
 * 
 */
public class CommonException extends RuntimeException {

    private static final long serialVersionUID = -5909003637017754887L;

    /**
     * 异常类型枚举
     */
    private CodeType          codeType;

    /**
     * 替换变量，如果枚举没有占位符，可设置空
     */
    private Object[]          placeholder;

    public CommonException(CodeType codeType, Object... placeholder) {
        super();
        this.codeType = codeType;
        this.placeholder = placeholder;
    }

    public CommonException(Throwable e, CodeType codeType, Object... placeholder) {
        super(e);
        this.codeType = codeType;
        this.placeholder = placeholder;
    }

    /**
     * 获取异常类型枚举
     * 
     * @return codeType 异常类型枚举
     */
    public CodeType getCodeType() {
        return codeType;
    }

    /**
     * 设置异常类型枚举
     * 
     * @param codeType 异常类型枚举
     */
    public void setCodeType(CodeType codeType) {
        this.codeType = codeType;
    }

    /**
     * 获取替换变量，如果枚举没有占位符，可设置空
     * 
     * @return placeholder 替换变量，如果枚举没有占位符，可设置空
     */
    public Object[] getPlaceholder() {
        return placeholder;
    }

    /**
     * 设置替换变量，如果枚举没有占位符，可设置空
     * 
     * @param placeholder 替换变量，如果枚举没有占位符，可设置空
     */
    public void setPlaceholder(Object... placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public String getMessage() {
        return MessageUtil.buildMessage(true, codeType, placeholder);
    }

}
