var url = "${adminServer}/weixinMenuConfig/query.htm";
var saveUrl = "${adminServer}/weixinMenuConfig/save.htm";
var getUrl = "${adminServer}/weixinMenuConfig/get.htm";
var deleteUrl = "${adminServer}/weixinMenuConfig/delete.htm";
var loadReplyComboUrl = "${adminServer}/weixinReplyConfig/query.htm?enable=1&rows=1000";
var loadParentMenuComboUrl = "${adminServer}/weixinMenuConfig/query.htm?parendId=-1&enable=1&rows=1000";
var createMenuUrl = "${adminServer}/weixinMenuConfig/createMenu.htm";
tdSize();// 必须放在外面
$(document).ready(function() {
	inintPage();
	appIdEvent();
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
	var menuName_temp = $.trim($("#menuName").val());
	var menuType_temp = $.trim($("#menuType").val());
	var parentId_temp = $.trim($("#parentId").val());
	var enable_temp = $.trim($("#enable").val());
	var appId_temp = $.trim($("#appId").val());
	var createTimeStart_temp = $.trim($("#createTimeStart").datebox('getValue'));
	var createTimeEnd_temp = $.trim($("#createTimeEnd").datebox('getValue'));
	var updateTimeStart_temp = $.trim($("#updateTimeStart").datebox('getValue'));
	var updateTimeEnd_temp = $.trim($("#updateTimeEnd").datebox('getValue'));
	var obj = {
		id : id_temp,

		appId : appId_temp,
		menuName : menuName_temp,

		menuType : menuType_temp,

		parentId : parentId_temp,

		enable : enable_temp,

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
		striped : true,
		rownumbers : true,
		pagination : true,
		method : 'GET',
		height : "70%",
		width : "100%",
		// animate : true,
		fitColumns : true,
		queryParams : getQueryParams(),
		url : url,
		toolbar : '#tbMenus',
		columns : [ [ {
			field : 'ck',
			checkbox : "true"
		}, {
			field : 'id',
			title : '主键',
			width : 80,
			align : 'center'
		}, {
			field : 'appId',
			title : '公众号',
			width : 150,
			align : 'center',
			formatter : function(val, rec) {
				if (!!val) {
					return $("#appId_" + val).data("desc");
				} else {
					return "商户公众号";
				}
			}
		},

		{
			field : 'menuName',
			title : '菜单名称',
			width : 200,
			align : 'center'
		},

		{
			field : 'menuType',
			title : '菜单类型',
			width : 100,
			align : 'center'
		},

		{
			field : 'parentId',
			title : '父层ID',
			width : 80,
			align : 'center'
		},

		{
			field : 'replyId',
			title : '回复ID',
			width : 100,
			align : 'center'
		}, {
			field : 'title',
			title : '回复标题',
			width : 200,
			align : 'center'
		},

		{
			field : 'sortNo',
			title : '排序码',
			width : 80,
			align : 'center'
		},

		{
			field : 'enable',
			title : '是否生效',
			width : 100,
			align : 'center',
			formatter : function(val, rec) {
				if (val == 1) {
					return "是";
				} else {
					return "否";
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
			title : '修改时间',
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
			msgShow("起始修改时间格式错误！");
			return false;
		}
	}
	if (updateTimeEnd.length > 0) {
		if (updateTimeEnd.match(regDateExpectTime) == null) {
			msgShow("结束修改时间格式错误！");
			return false;
		}
	}
	if (updateTimeStart && updateTimeEnd) {
		if (stringToDate(updateTimeStart) > stringToDate(updateTimeEnd)) {
			msgShow("结束修改时间必须大于起始修改时间!");
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

function appIdEvent() {
	$('#appIdAdd').combobox({
		onChange : function(newValue, oldValue) {
			initReplyCombogrid($("#replyIdAdd"));
			initParentMenuCombogrid($("#parentIdAdd"));
		}
	});
}
/**
 * 添加弹框
 */
function add() {
	$('#addDialog').dialog('open').dialog('setTitle', '添加');
	$('#addDialog').form('clear');

	$("#enableAdd").combobox('setValue', 1);
	initReplyCombogrid($("#replyIdAdd"));
	initParentMenuCombogrid($("#parentIdAdd"));

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

	initReplyCombogrid($("#replyIdAdd"));
	initParentMenuCombogrid($("#parentIdAdd"));

	$("#addForm").form('disableValidation');// 不验证，仅提交的时候验证

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
		keys.push(selected[i].id);
	}
	var appId = selected[0].appId;
	$.messager.confirm("提示信息", '您确定要删除当前选中的<strong style="color:red">' + keys.length + '</strong>条记录吗？', function(r) {
		if (r) {
			var param = {
				ids : keys.join(","),
				appId : appId
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
 * 创建菜单
 */
function createMenu(appId) {
	$.messager.confirm("提示信息", '您确定要创建微信菜单吗？', function(r) {
		if (r) {
			var param = {};
			ajaxLoad(createMenuUrl + "?appId=" + appId, "GET", param, function(data) {
				if (data.response && data.response.message) {
					// msgShow(data.response.message);
					msgAlert("提示信息", data.response.message, "info");
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

/**
 * 初始化回复combogrid
 */
function initReplyCombogrid($obj, data) {
	var columns = [ [ {
		field : 'id',
		title : '主键',
		width : 80,
		align : 'center'
	}, {
		field : 'replyType',
		title : '回复类型',
		width : 80,
		align : 'center',
		formatter : function(val, rec) {
			if (val == 1) {
				return "文本";
			} else if (val == 2) {
				return "链接";
			} else if (val == 3) {
				return "图文";
			} else if (val == 4) {
				return "小程序";
			} else {
				return "未知";
			}
		}
	}, {
		field : 'enable',
		title : '是否生效',
		width : 80,
		align : 'center',
		formatter : function(val, rec) {
			if (val == 1) {
				return "是";
			} else {
				return "否";
			}
		}
	}, {
		field : 'keywords',
		title : '关键字',
		width : 100,
		align : 'center'
	}, {
		field : 'title',
		title : '标题',
		width : 150,
		align : 'center'
	} ] ];

	$obj.combogrid({
		panelHeight : '300',
		width : 260,
		panelWidth : 550,
		editable : false,
		mode : 'local',
		// url : loadComboUrl,
		// method : 'get',
		// value : "请选择",
		idField : 'id',
		textField : 'title',
		columns : columns,
		filter : function(q, row) {
			var opts = $(this).combogrid('options');
			return row[opts.idField].indexOf(q) == 0;
		},
		onSelect : function(rowIndex, row) {
			if (row.replyType == 2) {
				$("#menuTypeAdd").val("view");
			} else if (row.replyType == 4) {
				$("#menuTypeAdd").val("miniprogram");
			} else {
				$("#menuTypeAdd").val("click");
			}
		}
	});
	if (!!data) {
		$obj.combogrid('grid').datagrid('loadData', data);
	} else {
		var appId = $("#appIdAdd").combobox('getValue');
		$obj.combogrid('grid').datagrid('options').url = loadReplyComboUrl + "&appId=" + appId;
		$obj.combogrid('grid').datagrid('options').method = 'get';
		$obj.combogrid('grid').datagrid('reload');
	}
}

/**
 * 初始化父节点combogrid
 */
function initParentMenuCombogrid($obj, data) {
	var columns = [ [ {
		field : 'id',
		title : '主键',
		width : 80,
		align : 'center'
	}, {
		field : 'menuName',
		title : '菜单名称',
		width : 180,
		align : 'center'
	}, {
		field : 'menuType',
		title : '菜单类型',
		width : 80,
		align : 'center'
	}, {
		field : 'title',
		title : '回复标题',
		width : 180,
		align : 'center'
	} ] ];

	$obj.combogrid({
		panelHeight : '300',
		width : 260,
		panelWidth : 550,
		// editable : false,
		mode : 'local',
		// url : loadComboUrl,
		// method : 'get',
		value : "-1",
		idField : 'id',
		textField : 'id',
		columns : columns,
		filter : function(q, row) {
			var opts = $(this).combogrid('options');
			return row[opts.idField].indexOf(q) == 0;
		},
		onSelect : function(rowIndex, row) {
		}
	});
	if (!!data) {
		$obj.combogrid('grid').datagrid('loadData', data);
	} else {
		var appId = $("#appIdAdd").combobox('getValue');
		$obj.combogrid('grid').datagrid('options').url = loadParentMenuComboUrl + "&appId=" + appId;
		$obj.combogrid('grid').datagrid('options').method = 'get';
		$obj.combogrid('grid').datagrid('reload');
	}
}
