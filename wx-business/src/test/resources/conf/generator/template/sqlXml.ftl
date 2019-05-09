<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd" >
<!-- ${entity.tableComment} -->
<mapper namespace="${entity.tableName?upper_case}">
	<!-- 获取满足条件的记录数量 -->
	<select id="QUERY_FOR_COUNT" parameterType="${entity.rootPackage}.bo.${entity.className}Bo" resultType="java.lang.Integer">
  	   	SELECT 
  	   		COUNT(1)
        FROM ${entity.tableName} 
        WHERE 1=1
   	   <#list entity.properties as property>
		<#if property.javaType=="Date">
		   ${r'<if test="'}${property.propertyName} ${r'!= null" >'}
		     AND ${property.colName} = ${r'#{'}${property.propertyName}}
		   ${r'</if>'}
   	       ${r'<if test="'}${property.propertyName}Start ${r'!= null" >'}
			 AND ${property.colName} <![CDATA[>=]]> ${r'#{'}${property.propertyName}Start}
		   ${r'</if>'}
		   ${r'<if test="'}${property.propertyName}End ${r'!= null" >'}
		     AND ${property.colName} <![CDATA[<=]]> ${r'#{'}${property.propertyName}End}
		   ${r'</if>'}
   	    <#elseif property.javaType=="String">
	   	   ${r'<if test="'}${property.propertyName} ${r'!= null and'} ${property.propertyName} ${r'!='}''${r'" >'}
		     AND ${property.colName} = ${r'#{'}${property.propertyName}}
		   ${r'</if>'}
		<#else>
	   	   ${r'<if test="'}${property.propertyName} ${r'!= null" >'}
		     AND ${property.colName} = ${r'#{'}${property.propertyName}}
		   ${r'</if>'}   
   	   	</#if>
   	   </#list>
	</select>


	<!-- 获取满足条件的记录列表 -->
	<select id="QUERY_FOR_LIST" parameterType="${entity.rootPackage}.bo.${entity.className}Bo" resultType="${entity.rootPackage}.model.${entity.className}">
		select
		<#list entity.properties as property>
		<#if !property_has_next>
			${property.colName} AS ${property.propertyName}
		<#else>
			${property.colName} AS ${property.propertyName},
		</#if>
		</#list>
		FROM
			${entity.tableName}
		WHERE 1=1
		   <#list entity.properties as property>
			<#if property.javaType=="Date">
			   ${r'<if test="'}${property.propertyName} ${r'!= null" >'}
			     AND ${property.colName} = ${r'#{'}${property.propertyName}}
			   ${r'</if>'}
	   	       ${r'<if test="'}${property.propertyName}Start ${r'!= null" >'}
				 AND ${property.colName} <![CDATA[>=]]> ${r'#{'}${property.propertyName}Start}
			   ${r'</if>'}
			   ${r'<if test="'}${property.propertyName}End ${r'!= null" >'}
			     AND ${property.colName} <![CDATA[<=]]> ${r'#{'}${property.propertyName}End}
			   ${r'</if>'}
	   	    <#elseif property.javaType=="String">
		   	   ${r'<if test="'}${property.propertyName} ${r'!= null and'} ${property.propertyName} ${r'!='}''${r'" >'}
			     AND ${property.colName} = ${r'#{'}${property.propertyName}}
			   ${r'</if>'}
			<#else>
		   	   ${r'<if test="'}${property.propertyName} ${r'!= null" >'}
			     AND ${property.colName} = ${r'#{'}${property.propertyName}}
			   ${r'</if>'}   
	   	   	</#if>
		   </#list>
		   LIMIT ${r'#{index},#{pageSize}'}
	</select>
</mapper>

