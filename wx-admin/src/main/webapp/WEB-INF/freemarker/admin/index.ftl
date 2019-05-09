<#import "/WEB-INF/freemarker/common/res_macro.ftl" as resMacro>
<!DOCTYPE html>
<html>
<head id="Head1">
    <title>后台管理系统</title>
	<@resMacro.jsurl resRoot="${javascriptServerAdmin}" url=["/js/index"] resConcat="${resConcat}" />

</head>
<body>
<div class="easyui-layout" data-options="fit:true" style="width:100%; height:100%;">
		<div data-options="region:'north',split:true" border="false" style="overflow: hidden; height: 40px;
        background: url(${imageServerAdmin}/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%;
        line-height: 30px;color: #fff; font-family: Verdana, 微软雅黑,黑体">
        	<span style="float:right; padding-right:20px;">
	        	<span style="font-weight:bold;line-height: 30px;color: #fff" id="bgclock"></span>
	        	<span style="font-weight:bold;line-height: 30px;color: #fff;padding-left:20px;">欢迎您，${user.accountName!""}</span>
	        	<a id="editPassword" href="#" class="easyui-linkbutton" style="font-weight:bold;line-height: 30px;color: #fff" data-options="plain:true,iconCls:'icon-edit'" onclick="javascript:openPasswordDialog();">修改密码</a>	        	
	        	<a id="loginOut" href="#" class="easyui-linkbutton" style="font-weight:bold;line-height: 30px;color: #fff" data-options="plain:true,iconCls:'icon-back'" onclick="javascript:logout();">安全退出</a>
        	</span>
        	<span style="padding-left:10px;"><img src="${imageServerAdmin}/images/blocks.gif" width="25" height="25" align="absmiddle" /><span style="padding-left:10px; font-size: 16px; font-weight:bold;">后台管理系统</span></span>
        </div>
		<div data-options="region:'south',split:true" style="height:30px;background: #D2E0F2;">
			<div style="text-align:center;color:#15428B; margin:0px; padding:0px;line-height:23px; font-weight:bold;">Copyright© 2015-2016</div>
		</div>
		<#--<div data-options="region:'east',split:true" title="East" style="width:100px;"></div>-->
		<div data-options="region:'west',split:true,tools:'#westTools'" title="导航菜单" style="width:240px;">
			<ul id="treeMenu" class="easyui-tree"></ul>
			
		</div>
		
		<div id="mainPanle" region="center" style="background: #eee; overflow-y:hidden">
			<div id="tabs" class="easyui-tabs"  fit="true" border="false" >
				<div title="欢迎使用" style="overflow:hidden;" closable="false" id="home">
					<#--天气-->
					<#--<div style="PADDING-RIGHT: 0px; PADDING-LEFT: 0px; PADDING-BOTTOM: 0px; PADDING-TOP: 0px; WIDTH: 100%; HEIGHT: 148px; border: 1px solid #cacaca; background: #FFFFFF">
						<div style="WIDTH: 100%; clear: both; height: 31px; background-image: url(http://www.tianqi.com/static/images/code/bg_13.jpg); background-repeat: repeat-x; border-bottom: 1px solid #cacaca;">
							<div style="float: left; height: 31px; color: #9e0905; font-weight: bold; line-height: 31px; margin-left: 20px; font-size: 14px;">城市天气预报</div>
						</div>
						<iframe width="800" scrolling="no" height="120" frameborder="0" allowtransparency="true" src="http://i.tianqi.com/index.php?c=code&id=19&color=%23&bgc=%23&bdc=%23C6C6C6&icon=1&py=chaoyang&temp=1&num=5"></iframe>
					</div>-->
					<#--<h1>Welcome，${user.accountName!""}</h1>-->
					<iframe name="mainFrame" scrolling="auto" frameborder="0"  src="//blog.guanyang.org" style="width:100%;height:100%;"></iframe>
				</div>
			</div>
		</div>
		<div id="mm" class="easyui-menu" style="width:150px;">
			<div id="mm-tabclose">关闭</div>
			<div id="mm-tabcloseall">全部关闭</div>
			<div id="mm-tabcloseother">关闭其他</div>
			<div class="menu-sep"></div>
			<div id="mm-tabcloseright">关闭右侧</div>
			<div id="mm-tabcloseleft">关闭左侧</div>
			<div class="menu-sep"></div>
			<div id="mm-exit">退出</div>
		</div>
		<div id="westTools">
			<a href="#" class="icon-redo" onclick="javascript:expandAllTreeMenu();"></a>
			<a href="#" class="icon-undo" onclick="javascript:collapseAllTreeMenu();"></a>
			<a href="#" class="icon-reload" onclick="javascript:reloadTreeMenu();"></a>
		</div>
    <#--修改密码窗口-->
		<div id="passwordDialog" class="easyui-dialog" title="修改密码" style="width: 360px; height: 300px;" 
		data-options="iconCls:'icon-edit',closed:true,modal:true" buttons="#passwordForm-buttons">
			<form id="passwordForm" method="post">
				<table class="formbox" border="0" cellpadding="5px" cellspacing="5px">
					<tr>
						<td class="label"><span style="color:red">*</span>原密码：</td>
						<td>
						<input id="oldPassword" class="easyui-validatebox" required="true" validType="length[2,30]" name="oldPassword" type="password" style="height:25px" />
						</td>
					</tr>
					<tr>
						<td class="label"><span style="color:red">*</span>新密码：</td>
						<td>
						<input id="newPassword" class="easyui-validatebox" required="true" validType="length[2,30]" name="newPassword" type="password" style="height:25px" />
						</td>
						
					</tr>
					<tr>
						<td class="label"><span style="color:red">*</span>确认密码：</td>
						<td><input id="confirmPassword" class="easyui-validatebox" required="true" validType="equals['#newPassword']" invalidMessage="两次密码不一致" type="password" style="height:25px" /></td>
					</tr>
				</table>
			</form>
		</div>
		<div id="passwordForm-buttons" region="center"
			style="text-align: center; margin: 0 atuo">
			<a href="#" class="easyui-linkbutton" iconCls="icon-ok"
				onclick="savePassword()">保存</a> <a href="#" class="easyui-linkbutton"
				iconCls="icon-cancel" onclick="closePasswordDialog()">取消</a>
		</div>
</div>
</body>
</html>