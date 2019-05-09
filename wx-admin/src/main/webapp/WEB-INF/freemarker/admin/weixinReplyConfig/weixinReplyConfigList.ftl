<#import "/WEB-INF/freemarker/common/res_macro.ftl" as resMacro>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>微信回复配置报表</title>
  </head>
  <body> 
  <#include "/WEB-INF/freemarker/admin/wxTypeHidden.ftl">
	<div id="searchPanel" class="easyui-panel" title="微信回复配置报表"
		style="text-align:left;width:auto;background: #fafafa;" collapsible="false"
		minimizable="false" maximizable="false" data-options="fit:true">
		
		<div class="grid-toolbar">
			<form id="searchFrm">
				<table class="tblContent" style="width:100%;">
							<tr>
								<td class="tdLeft"><span>主键:</span></td>
								<td class="tdRight"><input id="id" name="id"/></td>
								<td class="tdLeft"><span>关键字</span></td>
								<td class="tdRight"><input id="keywords" name="keywords"/></td>
								<td class="tdLeft"><span>标题:</span></td>
								<td class="tdRight"><input id="title" name="title"/></td>
								<td class="tdLeft"><span>是否生效:</span></td>
								<td class="tdRight">
									<select id="enable" name="enable">
									    <option value="">全部</option>
									    <option value="1">是</option>
									    <option value="0">否</option>
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
								<td class="tdLeft"><span>回复类型:</span></td>
								<td class="tdRight">
									<select id="replyType" name="replyType">
									    <option value="">所有类型</option>
									    <option value="1">文本</option>
									    <option value="2">链接</option>
									    <option value="3">图文</option>
									    <option value="4">小程序</option>
									</select>
								</td>
								<td class="tdLeft"><span>公众号:</span></td>
								<td class="tdRight">
									<select id="appId" name="appId">
									    <#include "/WEB-INF/freemarker/admin/wxType.ftl">
									</select>
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
			style="width: 60%; height: 80%;" closed="true" modal="true"
			buttons="#addForm-buttons" collapsible="false" minimizable="false" maximizable="true">
			<form id="addForm" method="post">
				<table class="formbox" border="0" cellpadding="5px" cellspacing="5px" style="width:100%">
							<input type="hidden" id="idAdd" name="id" />
								<tr>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>回复类型：</td>
									<td>
									<select class="easyui-combobox" required="true" id="replyTypeAdd" name="replyType" style="width:100px;" panelHeight="120px" editable="false">
									    <option value="1">文本</option>
									    <option value="2">链接</option>
									    <option value="3">图文</option>
									    <option value="4">小程序</option>
									</select>
									</td>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>关键字：</td>
									<td>
									<input class="easyui-validatebox" required="true" data-options="validType:'length[1,50]'" id="keywordsAdd" name="keywords"  type="text" />
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
									<td style="text-align:right;width:15%"><span style="color:red">*</span>标题：</td>
									<td>
									<input class="easyui-validatebox" required="true" data-options="validType:'length[1,60]'" id="titleAdd" name="title"  type="text" />
									</td>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>是否生效：</td>
									<td>
									<select class="easyui-combobox" required="true" id="enableAdd" name="enable" style="width:100px;" panelHeight="50px" editable="false">
									    <option value="1">是</option>
									    <option value="0">否</option>
									</select>
									</td>
								</tr>
								<tr>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>描述：</td>
									<td colspan="3">
									<textarea rows="3" cols="65%" class="easyui-validatebox" required="true" data-options="validType:'length[1,200]'" id="descriptionAdd" name="description"  type="text">
									</textarea>
									</td>
								</tr>
								<tr>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>文本：</td>
									<td colspan="3">
									<textarea rows="5" cols="65%" class="easyui-validatebox" required="true" data-options="validType:'length[1,500]'" id="replyTextAdd" name="replyText"  type="text">
									</textarea>
									</td>
								</tr>
								<tr>	
									<td style="text-align:right;width:15%"><span style="color:red">*</span>链接：</td>
									<td colspan="3">
									<input style="width:65%" class="easyui-validatebox" required="true" data-options="validType:['length[1,256]']" id="replyLinkAdd" name="replyLink"  type="text" />
									</td>
								</tr>
								<tr>
									<td style="text-align:right;width:15%"><span style="color:red">*</span>图片：</td>
									<td colspan="3">
									<input style="width:65%" class="easyui-validatebox" required="true" data-options="validType:['length[1,256]']" id="replyImgAdd" name="replyImg"  type="text" />
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
	<@resMacro.jsurl resRoot="${javascriptServerAdmin}" url=["/js/weixinReplyConfig/weixinReplyConfigList"] resConcat="${resConcat}" />
  </body>
</html>











