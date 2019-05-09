var rootPathAdmin = "${adminServer}";// 项目根路径
var url = rootPathAdmin + "/masterRedis/list.htm";
var getUrl = rootPathAdmin + "/masterRedis/get.htm";
var delUrl = rootPathAdmin + "/masterRedis/del.htm";
var loadComboUrl = rootPathAdmin + "/masterRedis/keys.htm";
var saveAddUrl = rootPathAdmin + "/masterRedis/set.htm";

tdSize();// 必须放在外面
$(document).ready(function() {
	init();
	loadComboFun();
});
function getQueryParams() {
	var obj = {
		prefix : $('#prefix').combobox('getValue')
	// prefix : $("#prefix").val()
	};
	return obj;
}

function init() {
	$('#tt').datagrid({
		url : "",
		method : 'GET',
		rownumbers : true,
		pagination : false,
		striped : true,
		width : "100%",
		height : 450,
		queryParams : getQueryParams(),
		toolbar : [ {
			id : 'queryRedis',
			text : '查看',
			iconCls : 'icon-search',
			handler : function() {
				get();
			}
		}, '-', {
			id : 'delConfig',
			text : '删除',
			iconCls : 'icon-no',
			handler : function() {
				del();
			}
		}, '-', {
			id : 'addConfig',
			text : '添加',
			iconCls : 'icon-add',
			handler : function() {
				openAdd();
			}
		} ],
		/*
		 * onDblClickRow : function(rowIndex, row) { if (row) { get(); } },
		 */
		columns : [ [ {
			field : 'ck',
			checkbox : "true"
		}, {
			field : 'key',
			title : 'key值',
			width : 380,
			align : 'center'
		}, {
			field : 'type',
			title : '储存类型',
			width : 80,
			align : 'center'
		}, {
			field : 'timeToLive',
			title : '存活期(秒)',
			width : 120,
			align : 'center',
			formatter : function(val, rec) {
				if (val == -2) {
					return '不存在';
				} else if (val == -1) {
					return '无剩余存活时间';
				} else {
					return val;
				}
			}
		} ] ]
	});
}

/**
 * 打开添加窗口
 */
function openAdd() {
	$('#addDialog').form('clear');
	$('#addDialog').dialog('open').dialog('setTitle', '添加缓存');
	$("#addForm").form('disableValidation');// 不验证，仅提交的时候验证
}
/**
 * 关闭添加窗口
 */
function closeAdd() {
	$('#addDialog').form('clear');
	$('#addDialog').dialog('close');
	$("#addForm").form('disableValidation');// 不验证，仅提交的时候验证
}
/**
 * 添加窗口保存
 */
function saveAdd() {
	$("#addForm").form('submit', {
		url : saveAddUrl,
		onSubmit : function() {
			$("#addForm").form('enableValidation');// 验证
			return $("#addForm").form('validate');
		},
		success : function(data) {
			var result = eval('(' + data + ')');
			if (result && result.success) {
				$('#addDialog').form('clear');
				$('#addDialog').dialog('close');
				msgShow("操作成功！");
			} else {
				$.messager.alert('提示信息', result.message, 'warning');
				// msgShow(result.message);
			}
		}
	});
}

function get(isConfirm) {
	var selected = $('#tt').datagrid('getSelections');
	if (!selected || selected.length == 0) {
		msgShow("请选择要操作的行！");
		return;
	}
	if (selected.length > 1) {
		msgShow("只能查看一行！");
		return;
	}
	$('#queryDialog').form('clear');
	$("#typeQuery").val(selected[0].type);
	$("#keyQuery").val(selected[0].key);
	$('#queryDialog').dialog('open').dialog('setTitle', '查询条件');
	// return;
	// $('#configDialog').form('clear');
	// var type = selected[0].type;
	// var url = getUrl + "?key=" + selected[0].key;
	// if (type == "set" || type == "string") {
	// $("#configForm").form("load", url);
	// $('#configDialog').dialog('open').dialog('setTitle', '查看信息');
	// } else if (type == "list" || type == "zset" || type == "hash") {
	// msgShow("hash、list和zset型需填写查询条件！");
	// $("#typeQuery").val(type);
	// $("#keyQuery").val(selected[0].key);
	// $('#queryDialog').dialog('open').dialog('setTitle', '查询条件');
	//
	// }
	// $("#configForm").form('disableValidation');// 不验证，仅提交的时候验证
}
/**
 * 关闭查看弹框
 */
function cannel() {
	$('#configDialog').form('clear');
	$('#configDialog').dialog('close');
}
/**
 * 后退
 */
function back() {
	$('#queryDialog').form('clear');
	var key = $("#key").val();
	var type = $("#type").val();
	$("#keyQuery").val(key);
	$("#typeQuery").val(type);
	$('#queryDialog').dialog('open').dialog('setTitle', '查询条件');
	$('#configDialog').form('clear');
	$('#configDialog').dialog('close');
}
/**
 * 关闭查询条件弹框
 */
function queryCannel() {
	$('#queryDialog').form('clear');
	$('#queryDialog').dialog('close');
}
/**
 * 查询条件弹框确认
 */
function querySave() {
	var keyQuery = $("#keyQuery").val();
	var typeQuery = $("#typeQuery").val();
	var startQuery = $("#startQuery").val();
	var endQuery = $("#endQuery").val();
	var fieldQuery = $("#fieldQuery").val();
	$.ajax({
		url : getUrl,
		type : "GET",
		cache : false,
		data : {
			key : keyQuery,
			type : typeQuery,
			start : startQuery,
			end : endQuery,
			field : fieldQuery
		},
		error : function(e) {
			$('#queryDialog').form('clear');
			$('#queryDialog').dialog('close');
			msgShow("系统异常，请稍后再试！");
		},
		success : function(data) {
			$('#queryDialog').form('clear');
			$('#queryDialog').dialog('close');
			if (data) {
				$('#configDialog').form('clear');
				$("#configForm").form("load", data);
				$("#value").val(obj2str(data.value));
				$('#configDialog').dialog('open').dialog('setTitle', '查看信息');
			} else {
				msgShow("系统异常，请稍后再试！");
			}
		}
	});
	// $("#queryForm").form('submit', {
	// url : getUrl,
	// onSubmit : function() {
	// },
	// success : function(data) {
	// $('#queryDialog').form('clear');
	// $('#queryDialog').dialog('close');
	// var result = eval('(' + data + ')');
	// $('#configDialog').form('clear');
	// $("#configForm").form("load", result);
	// // toJSONString();JSON.stringify();
	// $("#value").val(obj2str(result.value));
	// $('#configDialog').dialog('open').dialog('setTitle', '查看信息');
	// }
	// });
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
		keys.push(selected[i].key);
	}
	$.messager.confirm("提示信息", '您确定要删除当前选中的<strong style="color:red">' + keys.length + '</strong>条记录吗？', function(r) {
		if (r) {
			$.ajax({
				url : delUrl,
				type : "GET",
				cache : false,
				data : {
					keys : keys.join(",")
				},
				error : function(e) {
					msgShow("系统异常，请稍后再试！");
				},
				success : function(data) {
					if (data) {
						msgShow("成功删除<strong style='color:red'>" + data + "</strong>条记录！");
						reload();// 刷新页面
					} else {
						msgShow("系统异常，请稍后再试！");
					}
				}
			});
		}
	});
}

function loadComboFun() {
	$('#prefix').combogrid({
		panelHeight : '300',
		width : 260,
		panelWidth : 580,
		mode : 'local',
		url : loadComboUrl,
		method : 'get',
		value : 'GY:CACHE:CONFIG*',
		idField : 'value',
		textField : 'value',
		columns : [ [ {
			field : 'group',
			title : '类型',
			width : 50,
			sortable : true
		}, {
			field : 'value',
			title : '键值',
			width : 200,
			sortable : true,
			formatter : function(val, rec) {
				var s = '<span style="color:blue">' + val + '</span>';
				return s;
			}
		}, {
			field : 'expireTime',
			title : '有效期',
			width : 80,
			sortable : true
		} , {
			field : 'text',
			title : '描述',
			width : 200,
			sortable : true
		} ] ],
		filter : function(q, row) {
			var opts = $(this).combogrid('options');
			return row[opts.idField].indexOf(q) == 0;
		}
	});
}
/**
 * 查询
 */
function doSearch() {
	var prefix = $('#prefix').combobox('getValue');
	// var prefix = $("#prefix").val();
	if (!prefix) {
		msgShow("key前缀不能为空！");
		return;
	}
	if (prefix.indexOf("*") < 0) {
		msgShow("key前缀必须以'*'结束！");
		return;
	}
	// 避免重新初始化grid
	$("#tt").datagrid("options").url = url;
	$("#tt").datagrid("reload", getQueryParams());
}
/**
 * 重新加载
 */
function reload() {
	$("#tt").datagrid('reload');
}

/**
 * 重置查询条件
 */
function reset() {
	$('#searchForm').form('clear');
}