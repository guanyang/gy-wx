var url = "${adminServer}/sysUser/query.htm";
var saveUrl = "${adminServer}/sysUser/save.htm";
var getUrl = "${adminServer}/sysUser/get.htm";
var deleteUrl = "${adminServer}/sysUser/delete.htm";
var resetPasswordUrl = "${adminServer}/sysUser/reset.htm";
tdSize();// 必须放在外面
$(document).ready(function() {
	inintPage();
});

/**
 * 使用正则将字符串转成日期
 * 
 * @param strDate
 *            yyyy-MM-dd HH:mm:ss格式或yyyy-MM-dd格式字符串类型的日期 panchengliang/15061461
 */
function stringToDate(strDate) {
	strDate = strDate.replace(/-/g, "/");
	var date = new Date(strDate);
	return date;
}
var regDateExpectTime = /^(\d+)-(\d{1,2})-(\d{1,2}) (\d{1,2}):(\d{1,2}):(\d{1,2})$/;

function getQueryParams() {
	var id_temp = $.trim($("#id").val());
	var userName_temp = $.trim($("#userName").val());
	var accountName_temp = $.trim($("#accountName").val());
	var password_temp = $.trim($("#password").val());
	var salt_temp = $.trim($("#salt").val());
	var description_temp = $.trim($("#description").val());
	var status_temp = $.trim($("#status").val());
	var createTimeStart_temp = $.trim($("#createTimeStart").datebox('getValue'));
	var createTimeEnd_temp = $.trim($("#createTimeEnd").datebox('getValue'));
	var updateTimeStart_temp = $.trim($("#updateTimeStart").datebox('getValue'));
	var updateTimeEnd_temp = $.trim($("#updateTimeEnd").datebox('getValue'));
	var obj = {
		id : id_temp,

		userName : userName_temp,

		accountName : accountName_temp,

		password : password_temp,

		salt : salt_temp,

		description : description_temp,

		status : status_temp,

		createTimeStart : createTimeStart_temp,
		createTimeEnd : createTimeEnd_temp,

		updateTimeStart : updateTimeStart_temp,
		updateTimeEnd : updateTimeEnd_temp

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
		columns : [ [ {
			field : 'ck',
			checkbox : "true"
		}, {
			field : 'id',
			title : '主键',
			width : 100,
			align : 'center'
		},

		{
			field : 'userName',
			title : '昵称',
			width : 100,
			align : 'center'
		},

		{
			field : 'accountName',
			title : '账户',
			width : 150,
			align : 'center'
		},

		// {
		// field : 'password',
		// title : '密码',
		// width : 150,
		// align : 'center'
		// },
		//
		// {
		// field : 'salt',
		// title : '加盐值',
		// width : 150,
		// align : 'center'
		// },

		{
			field : 'description',
			title : '描述',
			width : 80,
			align : 'center'
		},

		{
			field : 'status',
			title : '状态',
			width : 150,
			align : 'center',
			formatter : function(val, rec) {
				if (val == 1) {
					return "启用";
				} else {
					return "禁用";
				}
			}
		},

		{
			field : 'createTime',
			title : '创建时间',
			width : 150,
			align : 'center',
			formatter : function(val, rec) {
				return formattime(val);
			}
		},

		{
			field : 'updateTime',
			title : '更新时间',
			width : 150,
			align : 'center',
			formatter : function(val, rec) {
				return formattime(val);
			}
		}

		] ],
		onDblClickRow : function(rowIndex, row) {
			if (row) {
				toEdit(row);
			}
		}
	});
}

/**
 * 查询
 */
function doSearch() {
	var createTimeStart = $.trim($("#createTimeStart").datebox('getValue'));
	var createTimeEnd = $.trim($("#createTimeEnd").datebox('getValue'));

	if (createTimeStart.length > 0) {
		if (createTimeStart.match(regDateExpectTime) == null) {
			msgShow("起始创建时间格式错误！");
			return false;
		}
	}
	if (createTimeEnd.length > 0) {
		if (createTimeEnd.match(regDateExpectTime) == null) {
			msgShow("结束创建时间格式错误！");
			return false;
		}
	}
	if (createTimeStart && createTimeEnd) {
		if (stringToDate(createTimeStart) > stringToDate(createTimeEnd)) {
			msgShow("结束创建时间必须大于起始创建时间!");
			return false;
		}
	}
	var updateTimeStart = $.trim($("#updateTimeStart").datebox('getValue'));
	var updateTimeEnd = $.trim($("#updateTimeEnd").datebox('getValue'));

	if (updateTimeStart.length > 0) {
		if (updateTimeStart.match(regDateExpectTime) == null) {
			msgShow("起始更新时间格式错误！");
			return false;
		}
	}
	if (updateTimeEnd.length > 0) {
		if (updateTimeEnd.match(regDateExpectTime) == null) {
			msgShow("结束更新时间格式错误！");
			return false;
		}
	}
	if (updateTimeStart && updateTimeEnd) {
		if (stringToDate(updateTimeStart) > stringToDate(updateTimeEnd)) {
			msgShow("结束更新时间必须大于起始更新时间!");
			return false;
		}
	}
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

	$("#statusAdd").combobox('setValue', 1);
	$("#confirmPassword").validatebox('enable');
	$("#passwordAdd").validatebox('enable');
	$("#accountNameAdd").validatebox('enable');
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

	// row.startTime = formattime(row.startTime);
	// row.endTime = formattime(row.endTime);

	$("#addForm").form("load", row);// 直接加在grid数据
	$('#addDialog').dialog('open').dialog('setTitle', '编辑');

	$("#confirmPassword").val(row.password);
	$("#confirmPassword").validatebox('disable');
	$("#passwordAdd").validatebox('disable');
	$("#accountNameAdd").validatebox('disable');

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
 * 批量重置密码
 */
function resetPassword() {
	var selected = $('#tt').datagrid('getSelections');
	if (!selected || selected.length == 0) {
		msgShow("请选择要操作的行！");
		return;
	}
	var ids = [];
	for (var i = 0; i < selected.length; i++) {
		ids.push(selected[i].id);
	}
	var defaultPassword = $("#defaultPassword").val();
	$.messager.confirm("提示信息", '您确定要重置当前选中的<strong style="color:red">' + ids.length + '</strong>条记录的密码为<strong style="color:red">' + defaultPassword + '</strong>吗？', function(r) {
		if (r) {
			var param = {
				ids : ids.join(",")
			};
			ajaxLoad(resetPasswordUrl, "POST", param, function(data) {
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
