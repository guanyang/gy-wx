package org.gy.framework.util.jedis;

/**
 * 功能描述：jedis封装对象
 * 
 * @version 2.0.0
 * @author guanyang
 */
public class JedisBean {
    /**
     * key值
     */
    private String  key;
    /**
     * 域值，逗号分隔
     */
    private String  field;
    /**
     * 起始索引
     */
    private Long    start;
    /**
     * 结束索引
     */
    private Long    end;
    /**
     * 超时时间，默认值1
     */
    private Integer time = 1;
    /**
     * 对应值
     */
    private Object  value;
    /**
     * 剩余存活时间
     */
    private Long    timeToLive;
    /**
     * 值存储类型
     */
    private String  type;

    /**
     * 排序字段值，升序
     */
    private Double  score;

    /**
     * 获取key值
     * 
     * @return key key值
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置key值
     * 
     * @param key key值
     */
    public void setKey(String key) {
        this.key = key;
    }

    /**
     * 获取域值，逗号分隔
     * 
     * @return field 域值，逗号分隔
     */
    public String getField() {
        return field;
    }

    /**
     * 设置域值，逗号分隔
     * 
     * @param field 域值，逗号分隔
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * 获取起始索引
     * 
     * @return start 起始索引
     */
    public Long getStart() {
        return start;
    }

    /**
     * 设置起始索引
     * 
     * @param start 起始索引
     */
    public void setStart(Long start) {
        this.start = start;
    }

    /**
     * 获取结束索引
     * 
     * @return end 结束索引
     */
    public Long getEnd() {
        return end;
    }

    /**
     * 设置结束索引
     * 
     * @param end 结束索引
     */
    public void setEnd(Long end) {
        this.end = end;
    }

    /**
     * 获取超时时间
     * 
     * @return time 超时时间
     */
    public Integer getTime() {
        return time;
    }

    /**
     * 设置超时时间
     * 
     * @param time 超时时间
     */
    public void setTime(Integer time) {
        this.time = time;
    }

    /**
     * 获取对应值
     * 
     * @return value 对应值
     */
    public Object getValue() {
        return value;
    }

    /**
     * 设置对应值
     * 
     * @param value 对应值
     */
    public void setValue(Object value) {
        this.value = value;
    }

    /**
     * 获取剩余存活时间
     * 
     * @return timeToLive 剩余存活时间
     */
    public Long getTimeToLive() {
        return timeToLive;
    }

    /**
     * 设置剩余存活时间
     * 
     * @param timeToLive 剩余存活时间
     */
    public void setTimeToLive(Long timeToLive) {
        this.timeToLive = timeToLive;
    }

    /**
     * 获取值存储类型
     * 
     * @return type 值存储类型
     */
    public String getType() {
        return type;
    }

    /**
     * 设置值存储类型
     * 
     * @param type 值存储类型
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * 获取排序字段值，升序
     * 
     * @return score 排序字段值，升序
     */
    public Double getScore() {
        return score;
    }

    /**
     * 设置排序字段值，升序
     * 
     * @param score 排序字段值，升序
     */
    public void setScore(Double score) {
        this.score = score;
    }

}
