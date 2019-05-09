<#import "/WEB-INF/freemarker/common/res_macro.ftl" as resMacro>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>后台用户记录报表</title>
  </head>
  <body> 
  	<input type="hidden" id="defaultPassword" value="${defaultPassword!'123456'}" />
	<div id="searchPanel" class="easyui-panel" title="后台用户记录报表"
		style="text-align:left;width:auto;background: #fafafa;" collapsible="false"
		minimizable="false" maximizable="false" data-options="fit:true">
		
		<div class="grid-toolbar">
			<form id="searchFrm">
				<table class="tblContent" style="width:100%;">
							<tr>
								<td class="tdLeft"><span>主键:</span></td>
								<td class="tdRight"><input id="id" name="id"/></td>
								<td class="tdLeft"><span>昵称:</span></td>
								<td class="tdRight"><input id="userName" name="userName"/></td>
								<td class="tdLeft"><span>账户:</span></td>
								<td class="tdRight"><input id="accountName" name="accountName"/></td>
								<td class="tdLeft"><span>状态:</span></td>
								<td class="tdRight">
									<select id="status" name="status">
									    <option value="">所有状态</option>
									    <option value="1">启用</option>
									    <option value="0">禁用</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="tdLeft">创建时间:</td>
									<td class="tdRight">
									<input id="createTimeStart" name="createTimeStart" class="easyui-datetimebox"></input>
								</td>
								<td class="tdTo">--------</td>
								<td class="tdRight">	
									<input id="createTimeEnd" name="createTimeEnd" class="easyui-datetimebox"></input>						
								</td>
								<td class="tdLeft">更新时间:</td>
									<td class="tdRight">
									<input id="updateTimeStart" name="updateTimeStart" class="easyui-datetimebox"></input>
								</td>
								<td class="tdTo">--------</td>
								<td class="tdRight">	
									<input id="updateTimeEnd" name="updateTimeEnd" class="easyui-datetimebox"></input>						
								</td>
 							</tr>
					
						
					
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
			<a href="javascript:void(0)" class="easyui-linkbutton" plain="true" iconCls="icon-cut" onclick="javascript:resetPassword();">批量重置密码</a>
		</div>
				<!--添加窗口-->
		<div id="addDialog" class="easyui-dialog" iconCls="icon-add"
			style="width: 50%; height: 40%;" closed="true" modal="true"
			buttons="#addForm-buttons">
			<form id="addForm" method="post">
				<table class="formbox" border="0" cellpadding="5px" cellspacing="5px" style="width:100%">
							<input type="hidden" id="idAdd" name="id" />
								<tr>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>昵称：</td>
									<td>
									<input class="easyui-validatebox" required="true" data-options="validType:'length[2,20]'" id="userNameAdd" name="userName"  type="text" />
									</td>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>账户：</td>
									<td>
									<input class="easyui-validatebox" required="true" data-options="validType:'length[2,30]'" id="accountNameAdd" name="accountName"  type="text" />
									</td>
								</tr>
								<tr>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>密码：</td>
									<td>
									<input class="easyui-validatebox" required="true" data-options="validType:'length[6,16]'" id="passwordAdd" name="password"  type="password" />
									</td>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>确认密码：</td>
									<td>
									<input class="easyui-validatebox" required="true" validType="equals['#passwordAdd']" invalidMessage="两次密码不一致" id="confirmPassword" type="password"  />
									</td>
								</tr>
								<tr>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>描述：</td>
									<td>
									<input class="easyui-validatebox" required="true" data-options="validType:'length[1,128]'" id="descriptionAdd" name="description"  type="text" />
									</td>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>状态：</td>
									<td>
									<select class="easyui-combobox" required="true" id="statusAdd" name="status" style="width:100px;" panelHeight="100px" editable="false">
									    <option value="1">启用</option>
									    <option value="0">禁用</option>
									</select>
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
	<@resMacro.jsurl resRoot="${javascriptServerAdmin}" url=["/js/sysUser/sysUserList"] resConcat="${resConcat}" />
  </body>
</html>











