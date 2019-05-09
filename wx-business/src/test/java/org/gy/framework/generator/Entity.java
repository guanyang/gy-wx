package org.gy.framework.generator;

import java.util.Date;
import java.util.List;

/**
 * 功能描述: 抽象实例类
 * 
 */
public class Entity {
    // 实体所在的包名
    private String         javaPackage;
    // 实体类名
    private String         className;
    // 首字母小写的类名
    private String         lowerClassName;
    // 父类名
    private String         superclass;
    // 属性集合
    private List<Property> properties;
    private List<String>   packages;
    // 是否有构造函数
    private boolean        constructors;
    private String         tableName;

    // 生成的文件名
    private String         fileName;
    // 表的注释用于ftl页面
    private String         tableComment;

    // 创建时间
    private Date           createDate;
    // 包的根路径
    private String         rootPackage;

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getJavaPackage() {
        return javaPackage;
    }

    public void setJavaPackage(String javaPackage) {
        this.javaPackage = javaPackage;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getSuperclass() {
        return superclass;
    }

    public void setSuperclass(String superclass) {
        this.superclass = superclass;
    }

    public List<Property> getProperties() {
        return properties;
    }

    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }

    public boolean isConstructors() {
        return constructors;
    }

    public void setConstructors(boolean constructors) {
        this.constructors = constructors;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public List<String> getPackages() {
        return packages;
    }

    public void setPackages(List<String> packages) {
        this.packages = packages;
    }

    /**
     * 获取fileName
     * 
     * @return fileName fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 设置fileName
     * 
     * @param fileName fileName
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLowerClassName() {
        return lowerClassName;
    }

    public void setLowerClassName(String lowerClassName) {
        this.lowerClassName = lowerClassName;
    }

    /**
     * 设置createDate
     * 
     * @param createDate createDate
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取createDate
     * 
     * @return createDate createDate
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 获取rootPackage
     * 
     * @return rootPackage rootPackage
     */
    public String getRootPackage() {
        return rootPackage;
    }

    /**
     * 设置rootPackage
     * 
     * @param rootPackage rootPackage
     */
    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

}
