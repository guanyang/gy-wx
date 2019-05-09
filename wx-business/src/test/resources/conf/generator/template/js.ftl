var url =  "${r'${adminServer}'}/${entity.lowerClassName}/query.htm";
var saveUrl = "${r'${adminServer}'}/${entity.lowerClassName}/save.htm";
var getUrl = "${r'${adminServer}'}/${entity.lowerClassName}/get.htm";
var deleteUrl = "${r'${adminServer}'}/${entity.lowerClassName}/delete.htm";
tdSize();// 必须放在外面
$(document).ready(function() {
	inintPage();
});

/**
 * 使用正则将字符串转成日期
 * @param strDate yyyy-MM-dd HH:mm:ss格式或yyyy-MM-dd格式字符串类型的日期 panchengliang/15061461
 */
function stringToDate(strDate){
    strDate= strDate.replace(/-/g,"/");
    var date = new Date(strDate);
    return date;
}	
var regDateExpectTime = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;

function getQueryParams() {
	<#list entity.properties as property>
			<#if property.javaType!="Date">
				var ${property.propertyName}_temp = $.trim($("#${property.propertyName}").val());
			<#else>
				var ${property.propertyName}Start_temp=$.trim($("#${property.propertyName}Start").datebox('getValue'));
				var ${property.propertyName}End_temp=$.trim($("#${property.propertyName}End").datebox('getValue'));
			</#if>	
	</#list>
	var obj = {
		<#list entity.properties as property>
				<#if property.javaType=="Date">
					<#if !property_has_next>
						${property.propertyName}Start : ${property.propertyName}Start_temp,
						${property.propertyName}End : ${property.propertyName}End_temp
					<#else>
						${property.propertyName}Start : ${property.propertyName}Start_temp,
						${property.propertyName}End : ${property.propertyName}End_temp,
					</#if>
				<#else>
					<#if !property_has_next>
						${property.propertyName} : ${property.propertyName}_temp
					<#else>
						${property.propertyName} : ${property.propertyName}_temp,
					</#if>	
				</#if>
				
		</#list>
	};
	return obj;
}










/**
 * 查询
 */
 

function inintPage() {
	$('#tt').datagrid({
		rownumbers : true,
		pagination : true,
		method : 'GET',
		height : "70%",
		width : "100%",
		queryParams : getQueryParams(),
		url : url,
		toolbar : '#tbMenus',
		columns : [ [  {
			field : 'ck',
			checkbox : "true"
		},
		<#list entity.properties as property>
				<#if property.javaType=="Date">
					<#if !property_has_next>
						{
							field : '${property.propertyName}',
							title : '${property.annotation}',
							width : 150,
							align : 'center',
							formatter : function(val, rec) {
								return formattime(val);
							}
						}
					<#else>
						{
							field : '${property.propertyName}',
							title : '${property.annotation}',
							width : 150,
							align : 'center',
							formatter : function(val, rec) {
								return formattime(val);
							}
						},
					</#if>
				<#else>
					<#if !property_has_next>
						{
							field : '${property.propertyName}',
							title : '${property.annotation}',
							width : 150,
							align : 'center'
						}
					<#else>
						{
							field : '${property.propertyName}',
							title : '${property.annotation}',
							width : 150,
							align : 'center'
						},
					</#if>	
				</#if>
				
		</#list>
		] ],
		onDblClickRow : function(rowIndex, row) {
			if (row) {
				// toEdit(row);
			}
		}
	});
}


/**
 * 查询
 */
function doSearch() {
	<#list entity.properties as property>
		<#if property.javaType=="Date">
			var ${property.propertyName}Start=$.trim($("#${property.propertyName}Start").datebox('getValue'));
			var ${property.propertyName}End=$.trim($("#${property.propertyName}End").datebox('getValue'));
		
		
			if (${property.propertyName}Start.length > 0) {
				if (${property.propertyName}Start.match(regDateExpectTime) == null) {
					msgShow("起始${property.annotation}格式错误！");
					return false;
				}
			}
			if (${property.propertyName}End.length > 0) {
				if (${property.propertyName}End.match(regDateExpectTime) == null) {
					msgShow("结束${property.annotation}格式错误！");
					return false;
				}
			}
			if(${property.propertyName}Start && ${property.propertyName}End){
				if(stringToDate(${property.propertyName}Start)>stringToDate(${property.propertyName}End)){
					msgShow("结束${property.annotation}必须大于起始${property.annotation}!");
					return false;
				}
			}
		</#if>
	</#list>	
			// 避免重新初始化grid
			$("#tt").datagrid("options").url = url;
			$("#tt").datagrid("reload", getQueryParams());
}



/**
 * 重置
 */
function doReset() {
	$('#searchFrm').form('clear');
}

/**
 * 重新加载
 */
function reload() {
	$("#tt").datagrid('reload');
}
/**
 * 添加弹框
 */
function add() {
	$('#addDialog').dialog('open').dialog('setTitle', '添加');
	$('#addDialog').form('clear');
	$("#addForm").form('disableValidation');// 不验证，仅提交的时候验证
}
/**
 * 关闭弹框
 */
function cancel() {
	$('#addForm').form('clear');
	$('#addDialog').dialog('close');
}
/**
 * 编辑
 */
function edit() {
	var selected = $('#tt').datagrid('getSelections');
	if (!selected || selected.length != 1) {
		msgShow("请选择要操作的一行记录！");
		return;
	}
	var row = selected[0];
	toEdit(row);
}

function toEdit(row) {
	$('#addDialog').form('clear');
	$("#addForm").form('disableValidation');// 不验证，仅提交的时候验证
	
	//TODO:时间需要格式化，不然load报错
	//row.startTime = formattime(row.startTime);
	//row.endTime = formattime(row.endTime);
	
	$("#addForm").form("load", row);// 直接加在grid数据
	$('#addDialog').dialog('open').dialog('setTitle', '编辑');

}

/**
 * 保存
 */
function save() {
	$("#addForm").form('submit', {
		url : saveUrl,
		onSubmit : function() {
			$("#addForm").form('enableValidation');// 验证
			return $("#addForm").form('validate');
		},
		success : function(data) {
			var result = eval('(' + data + ')');
			if (result.response && result.response.message) {
				msgShow(result.response.message);
				if (result.response.success) {
					cancel();
					reload();
				} else {
					$("#addForm").form('disableValidation');// 不验证，仅提交的时候验证
				}
			} else {
				msgShow("系统异常，请稍后再试！");
			}
		}
	});
}

/**
 * 删除
 */
function del() {
	var selected = $('#tt').datagrid('getSelections');
	if (!selected || selected.length == 0) {
		msgShow("请选择要操作的行！");
		return;
	}
	var keys = [];
	for (var i = 0; i < selected.length; i++) {
		//TODO:如果主键有调整，需要修改
		keys.push(selected[i].id);
	}
	$.messager.confirm("提示信息", '您确定要删除当前选中的<strong style="color:red">' + keys.length + '</strong>条记录吗？', function(r) {
		if (r) {
			var param = {
				ids : keys.join(",")
			};
			ajaxLoad(deleteUrl, "POST", param, function(data) {
				if (data.response && data.response.message) {
					msgShow(data.response.message);
					if (data.response.success) {
						reload();
					}
				} else {
					msgShow("系统异常，请稍后再试！");
				}
			});
		}
	});
}

/**
 * ajax请求封装
 * 
 * @param url
 * @param method
 * @param param
 * @param successCallback
 */
function ajaxLoad(url, method, param, successCallback) {
	$.ajax({
		url : url,
		type : method,
		cache : false,
		data : param,
		error : function(e) {
			msgShow("系统异常，请稍后再试！");
		},
		success : function(data) {
			if (!!data) {
				successCallback(data);
			} else {
				msgShow("系统异常，请稍后再试！");
			}
		}
	});
}
