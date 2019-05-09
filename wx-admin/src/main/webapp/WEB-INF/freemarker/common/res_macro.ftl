<#ftl strip_whitespace=true>
<#--
静态资源合并请求宏，请注意以下事项：
1、resRoot：静态资源路径；
2、url：静态资源集合，不带后缀名；
3、resConcat：是否合并处理，true支持，false不支持，可通过环境变量解决，SIT、PRE、PRD环境下设置为true，DEV环境下设置为false;
4、yui_compressor：项目必须配置压缩后缀;
5、versionNo：项目必须配置版本号;
参考示例：<@paiRes.jsurl resRoot="${javascriptServer2}" url=["/js/common/test1","/js/common/test2"] resConcat='false' />
-->
<#-- 
jsurl 格式化js的url加入版本号，用list形式可一次可以传入多个
-->
<#macro jsurl resRoot url=[] resConcat='false'>
<#if resRoot ? exists && resRoot!="">
<#if resConcat == 'true'>
<script type="text/javascript" src="${resRoot}/??<#list url as js>${js}<#if js?contains(".min")><#else>${yui_compressor}</#if>.js<#if js_has_next>,</#if></#list>?v=${versionNo}"></script>
<#else>
<#list url as js><script type="text/javascript" src="${resRoot}${js}<#if js?contains(".min")><#else>${yui_compressor}</#if>.js?v=${versionNo}" ></script></#list>
</#if>
</#if>
</#macro>
<#--
cssurl 格式化css的url加入版本号，用list形式可一次可以传入多个
-->
<#macro cssurl resRoot url=[] resConcat='false'>
<#if resRoot ? exists && resRoot!="">
<#if resConcat =='true'>
<link rel="stylesheet" type="text/css"  href="${resRoot}/??<#list url as css>${css}<#if css?contains(".min")><#else>${yui_compressor}</#if>.css<#if css_has_next>,</#if></#list>?v=${versionNo}" />
<#else>
<#list url as css><link rel="stylesheet" type="text/css" href="${resRoot}${css}<#if css?contains(".min")><#else>${yui_compressor}</#if>.css?v=${versionNo}" /></#list>
</#if>
</#if>
</#macro>