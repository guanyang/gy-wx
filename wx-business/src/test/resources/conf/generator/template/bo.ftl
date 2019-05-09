package ${entity.javaPackage};

<#list entity.packages as package>
import ${package};
</#list>

import org.gy.framework.util.generator.Pagination;

/**
 * 功能描述：${entity.tableComment}BO
 * 
 * @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
 */
public class ${entity.className}Bo extends Pagination{
<#--********** attribute ***********-->
<#list entity.properties as property>
    /**
    * ${property.annotation?replace("\n"," ")?replace("\r"," ")}
    */
    private ${property.javaType} ${property.propertyName};
    
    <#if property.javaType=="Date">
    /**
    * ${property.annotation}起始值
    */
    private ${property.javaType} ${property.propertyName}Start;
    /**
    * ${property.annotation}结束值
    */
    private ${property.javaType} ${property.propertyName}End;
    </#if>
</#list>
<#--********** get/set *************-->
<#list entity.properties as property>
    /**
    * 获取${property.annotation}
    * @return ${property.propertyName} ${property.annotation}
    */
    public ${property.javaType} get${property.propertyName?cap_first}() {
    	return ${property.propertyName};
    }
    /**
    * 设置${property.annotation}
    * @param ${property.propertyName} ${property.annotation}
    */
    public void set${property.propertyName?cap_first}(${property.javaType} ${property.propertyName}) {
    	this.${property.propertyName} = ${property.propertyName};
    }
    <#if property.javaType=="Date">
    /**
    * 获取${property.annotation}起始值
    * @return ${property.propertyName}Start ${property.annotation}起始值
    */
    public ${property.javaType} get${property.propertyName?cap_first}Start() {
    	return ${property.propertyName}Start;
    }
    /**
    * 设置${property.annotation}起始值
    * @param ${property.propertyName}Start ${property.annotation}起始值
    */
    public void set${property.propertyName?cap_first}Start(${property.javaType} ${property.propertyName}Start) {
    	this.${property.propertyName}Start = ${property.propertyName}Start;
    }
    /**
    * 获取${property.annotation}结束值
    * @return ${property.propertyName}End ${property.annotation}结束值
    */
    public ${property.javaType} get${property.propertyName?cap_first}End() {
    	return ${property.propertyName}End;
    }
    /**
    * 设置${property.annotation}结束值
    * @param ${property.propertyName}End ${property.annotation}结束值
    */
    public void set${property.propertyName?cap_first}End(${property.javaType} ${property.propertyName}End) {
    	this.${property.propertyName}End = ${property.propertyName}End;
    }
    </#if>
</#list>
}
