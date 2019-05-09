/**
* Copyright (C), 2011-2016, org.gy.sample
*
* @Author gy
* @Date ${entity.createDate?string("yyyy-MM-dd HH:mm:ss")}
*/
package ${entity.javaPackage};
<#list entity.packages as package>
import ${package};
</#list>

public class ${entity.className}DTO{
    <#--********** attribute ***********-->
<#list entity.properties as property>
	/**
     * ${property.annotation?replace("\n"," ")?replace("\r"," ")}
     */
    private ${property.javaType} ${property.propertyName};
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
</#list>
}
