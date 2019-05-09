<#import "/WEB-INF/freemarker/common/res_macro.ftl" as resMacro>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>主系统redis查询</title>
</head>
<body> 
	<#-- 表单查询 DIV 开始 -->
		<div id="searchPanel" class="easyui-panel" title="主系统redis查询"
			style="text-align:left;width:auto;background: #fafafa;" collapsible="false"
			minimizable="false" maximizable="false" data-options="fit:true">
	<#-- 表单查询 table 开始 -->
	<div class="grid-toolbar" id="searchForm">
	<form id="form1" name="form1" method="get">
	<table class="tblContent" style="width:100%;">
		<tr>
			<td class="tdLeft"><span>key前缀：</span> </td>
			<td class="tdRight">
			<#--<input id="prefix" name="prefix" value="YEB:PAI:CACHE*"/>
			<a id="prefixTooltip" class="easyui-linkbutton" data-options="plain: true, iconCls: 'icon-help'"></a>
			<select id="prefix" class="easyui-combobox" name="prefix" style="width:200px;">
			    <option value="YEB:PAI:CACHE*">YEB:PAI:CACHE*</option>
			    <option value="*">*</option>
			</select>-->
			<input id="prefix" name="prefix" class="easyui-combobox"><span style="color:blue">提示：必须以*结束，可输入</span>
			<td class="tdLeft"></td>
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
	<#--添加窗口-->
	<#--查看窗口-->
		<div id="configDialog" class="easyui-dialog"
			style="width: 660px; height: 300px;" closed="true"
			buttons="#configForm-buttons">
			<form id="configForm" method="post">
				<table class="formbox" border="0" cellpadding="5px" cellspacing="5px">
					<tr>
						<td class="label">key值：</td>
						<td>
						<input id="key"  name="key" type="text" value="" />
						</td>
						<td class="label">field域：</td>
						<td>
						<input id="field"  name="field" type="text" value="" />
						</td>
					</tr>
					<tr>
						<td class="label">起始索引：</td>
						<td>
						<input id="start"  name="start" type="text" value="" />
						</td>
						<td class="label">结束索引：</td>
						<td>
						<input id="end"  name="end" type="text" value="" />
						</td>
					</tr>
					<tr>
						<td class="label">存活时间：</td>
						<td>
						<input id="timeToLive"  name="timeToLive" type="text" value="" />
						</td>
						<td class="label">储存类型：</td>
						<td>
						<input id="type"  name="type" type="text" value="" />
						</td>
					</tr>
					<tr>
						<td class="label">对应值：</td>
						<td  colspan="3"><textarea rows="6" cols="60" id="value"  name="value">
							</textarea>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="configForm-buttons" region="center"
			style="text-align: center; margin: 0 atuo">
			<a href="#" class="easyui-linkbutton" iconCls="icon-undo"
				onclick="back()">后退</a><a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="cannel()">关闭</a>
		</div>
		<#--查看窗口-->
		<div id="queryDialog" class="easyui-dialog"
			style="width: 360px; height: 320px;" closed="true"
			buttons="#queryForm-buttons">
			<form id="queryForm" method="get">
				<table class="formbox" border="0" cellpadding="5px" cellspacing="5px">
					<tr>
						<td class="label" style="color:blue">储存类型：</td>
						<td>
						<input id="typeQuery" style="color:blue" name="type" type="text" readonly="readonly"/>
						</td>
					</tr>
					<tr>
						<td class="label">key值：</td>
						<td>
						<input id="keyQuery"  name="key" type="text" readonly="readonly" />
						</td>
					</tr>
					<tr>
						<td class="label">field域：</td>
						<td>
						<input id="fieldQuery"  name="field" type="text" value="" />
						</td>
					</tr>
					<tr>
						<td class="label">start值：</td>
						<td>
						<input id="startQuery"  name="start" type="text" value="" />
						</td>
					</tr>
					<tr>
						<td class="label">end值：</td>
						<td>
						<input id="endQuery"  name="end" type="text" value="" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<span id="tipQuery" style="color:grey">说明：hash型需填写field，非必填，zset和list型需填写start和end，非必填</span>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="queryForm-buttons" region="center"
			style="text-align: center; margin: 0 atuo">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="querySave()">确定</a><a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="queryCannel()">取消</a>
		</div>
		
		<#--添加窗口-->
		<div id="addDialog" class="easyui-dialog"
			style="width: 360px; height: 350px;" closed="true"
			buttons="#addForm-buttons">
			<form id="addForm" method="get">
				<table class="formbox" border="0" cellpadding="5px" cellspacing="5px">
					<tr>
						<td class="label" style="color:blue"><span style="color:red">*</span>储存类型：</td>
						<td>
						<select id="addType" name="type" required="true" class="easyui-combobox" style="width:180px;" data-options="panelHeight:'auto'">
			    			<option value="string">string</option>
			    			<option value="hash">hash</option>
			    			<option value="list">list</option>
			    			<option value="set">set</option>
			    			<option value="zset">zset</option>
						</select>
						</td>
					</tr>
					<tr>
						<td class="label"><span style="color:red">*</span>key值：</td>
						<td>
						<input id="addKey" name="key" class="easyui-validatebox" required="true" type="text" />
						</td>
					</tr>
					<tr>
						<td class="label"><span style="color:red">*</span>value值：</td>
						<td>
						<input id="addValue" name="value" class="easyui-validatebox" required="true" type="text" />
						</td>
					</tr>
					<tr>
						<td class="label"><span style="color:red">*</span>expire值：</td>
						<td>
						<input id="addTime"  name="time" class="easyui-numberbox" required="true" min="1" type="text" />
						</td>
					</tr>
					<tr>
						<td class="label">field值：</td>
						<td>
						<input id="addfield"  name="field" type="text" />
						</td>
					</tr>
					<tr>
						<td class="label">score值：</td>
						<td>
						<input id="addScore"  name="score" type="text" />
						</td>
					</tr>
					<tr>
						<td colspan="2">
						<span id="addQuery" style="color:grey">说明：hash型必须填写field，zset型必须填写score</span>
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div id="addForm-buttons" region="center"
			style="text-align: center; margin: 0 atuo">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="saveAdd()">确定</a><a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="closeAdd()">取消</a>
		</div>
 </div>
	<@resMacro.jsurl resRoot="${javascriptServerAdmin}" url=["/js/masterRedis/masterRedis"] resConcat="${resConcat}" />
</body>
</html>