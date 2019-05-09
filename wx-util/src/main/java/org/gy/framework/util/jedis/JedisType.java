package org.gy.framework.util.jedis;

/**
 * 功能描述：Jedis所储存的值类型枚举
 * 
 * @version 2.0.0
 * @author guanyang
 */
public enum JedisType {

    /**
     * key不存在
     */
    NONE("none", "key不存在"),
    /**
     * 字符串
     */
    STRING("string", "字符串"),
    /**
     * 列表
     */
    LIST("list", "列表"),
    /**
     * 集合
     */
    SET("set", "集合"),
    /**
     * 有序集合
     */
    ZSET("zset", "有序集合"),
    /**
     * 哈希表
     */
    HASH("hash", "哈希表");

    /**
     * 标识码
     */
    private String code;
    /**
     * 描述
     */
    private String description;

    private JedisType(String code, String description) {
        this.code = code;
        this.description = description;
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
     * 设置标识码
     * 
     * @param code 标识码
     */
    public void setCode(String code) {
        this.code = code;
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
     * 设置描述
     * 
     * @param description 描述
     */
    public void setDescription(String description) {
        this.description = description;
    }

}
