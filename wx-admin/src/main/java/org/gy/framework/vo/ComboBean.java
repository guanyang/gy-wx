package org.gy.framework.vo;

/**
 * 功能描述：组合框封装对象
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class ComboBean {

    /**
     * 描述
     */
    private String text;

    /**
     * 对应值
     */
    private String value;

    /**
     * 对应组
     */
    private String group;

    /**
     * 有效期
     */
    private int    expireTime;

    /**
     * 获取有效期
     * 
     * @return expireTime 有效期
     */
    public int getExpireTime() {
        return expireTime;
    }

    /**
     * 设置有效期
     * 
     * @param expireTime 有效期
     */
    public void setExpireTime(int expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * 获取描述
     * 
     * @return text 描述
     */
    public String getText() {
        return text;
    }

    /**
     * 设置描述
     * 
     * @param text 描述
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 获取对应值
     * 
     * @return value 对应值
     */
    public String getValue() {
        return value;
    }

    /**
     * 设置对应值
     * 
     * @param value 对应值
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * 获取对应组
     * 
     * @return group 对应组
     */
    public String getGroup() {
        return group;
    }

    /**
     * 设置对应组
     * 
     * @param group 对应组
     */
    public void setGroup(String group) {
        this.group = group;
    }

}
