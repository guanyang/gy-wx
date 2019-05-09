<#--微信公众号类型-->
<#if wxType??>
<#list wxType?keys as key>
<input type="hidden" id="appId_${key}" data-desc="${wxType[key]}" value="${key}" />
</#list>
</#if>