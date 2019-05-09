package org.gy.framework.generator;

/**
 * 功能描述: 属性和列
 * 
 */
public class Property {
    /** 注释 */
    private String  annotation;
    /** 属性数据类型 */
    private String  javaType;
    /** 属性名称 */
    private String  propertyName;
    /** 列名 */
    private String  colName;
    /** 列顺序 */
    private int     ordinalPosition;
    private String  extra;
    /** 是否是主键 */
    private boolean pk;
    /** 是否自增 **/
    private boolean autoIncrement;

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getJavaType() {
        return javaType;
    }

    public void setJavaType(String javaType) {
        this.javaType = javaType;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public boolean isPk() {
        return pk;
    }

    public void setPk(boolean pk) {
        this.pk = pk;
    }

    public int getOrdinalPosition() {
        return ordinalPosition;
    }

    public void setOrdinalPosition(int ordinalPosition) {
        this.ordinalPosition = ordinalPosition;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    /**
     * 获取是否自增
     * 
     * @return autoIncrement 是否自增
     */
    public boolean isAutoIncrement() {
        return autoIncrement;
    }

    /**
     * 设置是否自增
     * 
     * @param autoIncrement 是否自增
     */
    public void setAutoIncrement(boolean autoIncrement) {
        this.autoIncrement = autoIncrement;
    }

}
