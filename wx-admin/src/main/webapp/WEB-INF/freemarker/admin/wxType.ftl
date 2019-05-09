<#--微信公众号类型-->
<#if wxType??>
<#list wxType?keys as key>
　　<option value="${key}">${wxType[key]}</option>
</#list>
</#if>