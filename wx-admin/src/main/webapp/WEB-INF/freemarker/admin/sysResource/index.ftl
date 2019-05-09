<#import "/WEB-INF/freemarker/common/res_macro.ftl" as resMacro>
<html>
<head>
<title>菜单配置</title>
</head>
<body> 
	<#-- 表单查询 DIV 开始 -->
		<div id="searchPanel" class="easyui-panel" title="菜单配置查询"
			style="text-align:left;width:auto;background: #fafafa;" collapsible="false"
			minimizable="false" maximizable="false" data-options="fit:true" border="false" >
	<#-- 表单查询 table 开始 -->
	<div class="grid-toolbar" id="searchForm">
	<form id="form1" name="form1" method="get">
	<table class="tblContent" style="width:100%;">
		<tr>
			<td class="tdLeft"><span>菜单名称：</span> </td>
			<td class="tdRight"><input id="name" name="name" /></td>
			<td class="tdLeft"><span>状态：</span> </td>
			<td class="tdRight">
				<select id="status" name="status">
					<option value="">所有状态</option>
				    <option value="0">未启用</option>
				    <option value="1">启用</option>
				</select>
			</td>
			<td class="tdRight">	
				<a href="javascript:void(0);"  class="easyui-linkbutton" data-options="iconCls:'icon-search'"  onclick="doSearch()">查询</a>
				<a href="javascript:void(0);"  class="easyui-linkbutton" data-options="iconCls:'icon-undo'"  onclick="reset()">重置</a>
			</td>
		</tr>
	</table>
	</form>
	</div>
	<div id="tt" class="grid-auto">
	</div>
	<div id="tbMenus" style="padding:2px;height:auto">
		<a href="javascript:void(0)" class="easyui-menubutton" plain="true" iconCls="icon-add" data-options="menu:'#addMenu'">添 加</a>
		<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-edit" onclick="javascript:edit();">修 改 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-no" onclick="javascript:del();">删 除 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-save" onclick="javascript:save();">保存 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="javascript:cancel();">取消 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-reload" onclick="javascript:doSearch();">刷新 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-redo" onclick="javascript:expandAll();">展开 </a>
		<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-undo" onclick="javascript:collapseAll();">折叠 </a>
	</div>
	<div id="addMenu" style="width:100px;">
	    <div iconCls="icon-add" onclick="javascript:add(false);">一级节点</div>
	    <div iconCls="icon-add" onclick="javascript:add(true);">子节点</div>
	</div>
 </div>
	<@resMacro.jsurl resRoot="${javascriptServerAdmin}" url=["/js/sysResource/config"] resConcat="${resConcat}" />
</body>
</html>