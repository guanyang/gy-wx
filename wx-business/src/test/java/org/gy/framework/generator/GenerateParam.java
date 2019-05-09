package org.gy.framework.generator;

import java.util.List;

/**
 * 功能描述：模板生成参数封装
 * 
 */
public class GenerateParam {

    /**
     * 数据库url
     */
    private String       url;
    /**
     * 数据库用户名
     */
    private String       user;
    /**
     * 数据库密码
     */
    private String       password;
    /**
     * 数据库实例名
     */
    private String       schema;
    /**
     * 原数据库表名
     */
    private String[]     tableNames;
    /**
     * 目标存放路径
     */
    private String       targetPath;
    /**
     * 目标包名称
     */
    private String       targetJavaPackage;
    /**
     * 模板文件路径
     */
    private String       templatePath;
    /**
     * 模板名称
     */
    private String       templateName;
    /**
     * 生成文件扩展名
     */
    private String       fileExt;
    /**
     * 文件名尾部追加
     */
    private String       fileSuffix;
    /**
     * 文件名首部追加
     */
    private String       filePrefix;

    /**
     * 映射实体封装集合
     */
    private List<Entity> entities;

    /**
     * 包的根路径
     */
    private String       rootPackage;

    /**
     * 获取数据库url
     * 
     * @return url 数据库url
     */
    public String getUrl() {
        return url;
    }

    /**
     * 获取数据库用户名
     * 
     * @return user 数据库用户名
     */
    public String getUser() {
        return user;
    }

    /**
     * 获取数据库密码
     * 
     * @return password 数据库密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 获取数据库实例名
     * 
     * @return schema 数据库实例名
     */
    public String getSchema() {
        return schema;
    }

    /**
     * 获取原数据库表名
     * 
     * @return tableNames 原数据库表名
     */
    public String[] getTableNames() {
        return tableNames;
    }

    /**
     * 设置数据库url
     * 
     * @param url 数据库url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * 设置数据库用户名
     * 
     * @param user 数据库用户名
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     * 设置数据库密码
     * 
     * @param password 数据库密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 设置数据库实例名
     * 
     * @param schema 数据库实例名
     */
    public void setSchema(String schema) {
        this.schema = schema;
    }

    /**
     * 设置原数据库表名
     * 
     * @param tableNames 原数据库表名
     */
    public void setTableNames(String[] tableNames) {
        this.tableNames = tableNames;
    }

    /**
     * 获取目标存放路径
     * 
     * @return targetPath 目标存放路径
     */
    public String getTargetPath() {
        return targetPath;
    }

    /**
     * 设置目标存放路径
     * 
     * @param targetPath 目标存放路径
     */
    public void setTargetPath(String targetPath) {
        this.targetPath = targetPath;
    }

    /**
     * 获取目标包名称
     * 
     * @return targetJavaPackage 目标包名称
     */
    public String getTargetJavaPackage() {
        return targetJavaPackage;
    }

    /**
     * 设置目标包名称
     * 
     * @param targetJavaPackage 目标包名称
     */
    public void setTargetJavaPackage(String targetJavaPackage) {
        this.targetJavaPackage = targetJavaPackage;
    }

    /**
     * 获取模板文件路径
     * 
     * @return templatePath 模板文件路径
     */
    public String getTemplatePath() {
        return templatePath;
    }

    /**
     * 设置模板文件路径
     * 
     * @param templatePath 模板文件路径
     */
    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * 获取模板名称
     * 
     * @return templateName 模板名称
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * 设置模板名称
     * 
     * @param templateName 模板名称
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * 获取生成文件扩展名
     * 
     * @return fileExt 生成文件扩展名
     */
    public String getFileExt() {
        return fileExt;
    }

    /**
     * 设置生成文件扩展名
     * 
     * @param fileExt 生成文件扩展名
     */
    public void setFileExt(String fileExt) {
        this.fileExt = fileExt;
    }

    /**
     * 获取文件名尾部追加
     * 
     * @return fileSuffix 文件名尾部追加
     */
    public String getFileSuffix() {
        return fileSuffix;
    }

    /**
     * 设置文件名尾部追加
     * 
     * @param fileSuffix 文件名尾部追加
     */
    public void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    /**
     * 获取文件名首部追加
     * 
     * @return filePrefix 文件名首部追加
     */
    public String getFilePrefix() {
        return filePrefix;
    }

    /**
     * 设置文件名首部追加
     * 
     * @param filePrefix 文件名首部追加
     */
    public void setFilePrefix(String filePrefix) {
        this.filePrefix = filePrefix;
    }

    /**
     * 获取映射实体封装集合
     * 
     * @return entities 映射实体封装集合
     */
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * 设置映射实体封装集合
     * 
     * @param entities 映射实体封装集合
     */
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    /**
     * 获取包的根路径
     * 
     * @return rootPackage 包的根路径
     */
    public String getRootPackage() {
        return rootPackage;
    }

    /**
     * 设置包的根路径
     * 
     * @param rootPackage 包的根路径
     */
    public void setRootPackage(String rootPackage) {
        this.rootPackage = rootPackage;
    }

}
