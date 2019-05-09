<#import "/WEB-INF/freemarker/common/res_macro.ftl" as resMacro>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>微信回复日志报表</title>
  </head>
  <body> 
  <#include "/WEB-INF/freemarker/admin/wxTypeHidden.ftl">
	<div id="searchPanel" class="easyui-panel" title="微信回复日志报表"
		style="text-align:left;width:auto;background: #fafafa;" collapsible="false"
		minimizable="false" maximizable="false" data-options="fit:true">
		
		<div class="grid-toolbar">
			<form id="searchFrm">
				<table class="tblContent" style="width:100%;">
							<tr>
								<td class="tdLeft"><span>主键:</span></td>
								<td class="tdRight"><input id="id" name="id"/></td>
								<td class="tdLeft"><span>微信用户id:</span></td>
								<td class="tdRight"><input id="openId" name="openId"/></td>
								<td class="tdLeft"><span>类型</span></td>
								<td class="tdRight"><input id="type" name="type"/></td>
								<td class="tdLeft"><span>公众号:</span></td>
								<td class="tdRight">
									<select id="appId" name="appId">
									    <#include "/WEB-INF/freemarker/admin/wxType.ftl">
									</select>
								</td>
							</tr>
							<tr>
								<td class="tdLeft">操作时间:</td>
									<td class="tdRight">
									<input id="createTimeStart" name="createTimeStart" class="easyui-datetimebox"></input>
								</td>
								<td class="tdTo">--------</td>
								<td class="tdRight">	
									<input id="createTimeEnd" name="createTimeEnd" class="easyui-datetimebox"></input>						
								</td>
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
			style="width: 40%; height: 60%;" closed="true" modal="true"
			buttons="#addForm-buttons">
			<form id="addForm" method="post">
				<table class="formbox" border="0" cellpadding="5px" cellspacing="5px" style="width:100%">
							<input type="hidden" id="idAdd" name="id" />
								<tr>
									<td style="text-align:right;"><span style="color:red">*</span>微信用户id：</td>
									<td>
									<input class="easyui-validatebox" required="true" data-options="validType:'length[1,32]'" id="openIdAdd" name="openId"  type="text" />
									</td>
								</tr>
								<tr>	
									<td style="text-align:right;"><span style="color:red">*</span>类型</td>
									<td>
									<input class="easyui-validatebox" required="true" data-options="validType:'length[1,24]'" id="typeAdd" name="type"  type="text" />
									</td>
								</tr>
								<tr>
									<td style="text-align:right;"><span style="color:red"></span>公众号：</td>
									<td>
									<select class="easyui-combobox" id="appIdAdd" name="appId" style="width:100px;" panelHeight="80px" editable="false">
									    <#include "/WEB-INF/freemarker/admin/wxType.ftl">
									</select>
									</td>
								</tr>
								<tr>
									</td>
									<td style="text-align:right;"><span style="color:red">*</span>内容：</td>
									<td><textarea rows="5" cols="30" class="easyui-validatebox" required="true" data-options="validType:'length[1,500]'" id="contentAdd" name="content"  type="text">
										</textarea>
									</td>
								</tr>
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
	<@resMacro.jsurl resRoot="${javascriptServerAdmin}" url=["/js/weixinReplyLog/weixinReplyLogList"] resConcat="${resConcat}" />
  </body>
</html>











