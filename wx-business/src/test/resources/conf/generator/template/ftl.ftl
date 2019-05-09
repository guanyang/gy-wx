${r'<#import "/WEB-INF/freemarker/common/res_macro.ftl" as resMacro>'}
<#setting url_escaping_charset='utf-8'>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>${entity.tableComment}报表</title>
  </head>
  <body> 
	<div id="searchPanel" class="easyui-panel" title="${entity.tableComment}报表"
		style="text-align:left;width:auto;background: #fafafa;" collapsible="false"
		minimizable="false" maximizable="false" data-options="fit:true">
		
		<div class="grid-toolbar">
			<form id="searchFrm">
				<table class="tblContent" style="width:100%;">
					<#list entity.properties as property>
						<#if property.javaType!="Date">
							<tr>
								<td class="tdLeft"><span>${property.annotation}:</span></td>
								<td class="tdRight"><input id="${property.propertyName}" name="${property.propertyName}"/></td>
							</tr>
						<#else>
							<tr>
								<td class="tdLeft">${property.annotation}:</td>
									<td class="tdRight">
									<input id="${property.propertyName}Start" name="${property.propertyName}Start" class="easyui-datetimebox"></input>
								</td>
								<td class="tdTo">--------</td>
								<td class="tdRight">	
									<input id="${property.propertyName}End" name="${property.propertyName}End" class="easyui-datetimebox"></input>						
								</td>
 							</tr>
						</#if>
					</#list>
					
						
					
					<tr>
						<td class="tdLeft"><span>&nbsp;</span></td>
						<td>
							<div style="float:right; ">
								<a href="javascript:void(0);" class="easyui-linkbutton" icon="icon-search" onclick="doSearch()">查询</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-no'" onclick="doReset()">重置</a>
							</div>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<!-- 表单查询 结果DIV开始 --> 
		<div id="tt" class="grid-auto"></div>
		<!--工具条-->
		<div id="tbMenus" style="padding:2px;height:auto">
			<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-add" onclick="javascript:add();">添 加</a>
			<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-edit" onclick="javascript:edit();">修 改 </a>
			<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-no" onclick="javascript:del();">删除 </a>
			<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-reload" onclick="javascript:reload();">刷新 </a>
		</div>
				<!--添加窗口-->
		<div id="addDialog" class="easyui-dialog" iconCls="icon-add"
			style="width: 60%; height: 50%;" closed="true" modal="true"
			buttons="#addForm-buttons">
			<form id="addForm" method="post">
				<table class="formbox" border="0" cellpadding="5px" cellspacing="5px" style="width:100%">
					<#--非主键计数-->
					<#assign numNoPk=0 />
					<#list entity.properties as property>
						<#if property.pk>
							<input type="hidden" id="${property.propertyName}Add" name="${property.propertyName}" />
						<#else>
							<#if numNoPk%2==0>
								<tr>
							</#if>
							<#if property.javaType!="Date">
									<td style="text-align:right;width:15%"><span style="color:red">*</span>${property.annotation}：</td>
									<td>
									<input class="easyui-validatebox" required="true" data-options="validType:'length[1,128]'" id="${property.propertyName}Add" name="${property.propertyName}"  type="text" />
									</td>
							<#else>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>${property.annotation}：</td>
									<td>
									<input class="easyui-datetimebox" required="true" id="${property.propertyName}Add" name="${property.propertyName}" type="text" />
									</td>
							</#if>
							<#if numNoPk%2==1||!property_has_next>
								</tr>
							</#if>
							<#assign numNoPk=numNoPk+1 />	
						</#if>
					</#list>
				</table>
			</form>
		</div>
		<div id="addForm-buttons" region="center"
			style="text-align: center; margin: 0 atuo">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="save()">保存</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="cancel()">取消</a>
		</div>
 	 </div>
	<!--引入js路径-->
	${r'<@resMacro.jsurl resRoot="${javascriptServerAdmin}"'} url=["/js/${entity.lowerClassName}/${entity.className}List"] ${r'resConcat="${resConcat}" />'}
  </body>
</html>











