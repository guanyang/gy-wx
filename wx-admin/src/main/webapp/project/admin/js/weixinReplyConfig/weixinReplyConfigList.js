var url = "${adminServer}/weixinReplyConfig/query.htm";
var saveUrl = "${adminServer}/weixinReplyConfig/save.htm";
var getUrl = "${adminServer}/weixinReplyConfig/get.htm";
var deleteUrl = "${adminServer}/weixinReplyConfig/delete.htm";
tdSize();// 必须放在外面
$(document).ready(function() {
	inintPage();
	replyTypeEvent();
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
	var keywords_temp = $.trim($("#keywords").val());
	var title_temp = $.trim($("#title").val());
	var replyType_temp = $.trim($("#replyType").val());
	var enable_temp = $.trim($("#enable").val());
	var appId_temp = $.trim($("#appId").val());
	var createTimeStart_temp = $.trim($("#createTimeStart").datebox('getValue'));
	var createTimeEnd_temp = $.trim($("#createTimeEnd").datebox('getValue'));
	var updateTimeStart_temp = $.trim($("#updateTimeStart").datebox('getValue'));
	var updateTimeEnd_temp = $.trim($("#updateTimeEnd").datebox('getValue'));
	var obj = {
		id : id_temp,

		keywords : keywords_temp,
		appId : appId_temp,

		title : title_temp,

		replyType : replyType_temp,

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
		},

		{
			field : 'keywords',
			title : '关键字',
			width : 100,
			align : 'center'
		},

		{
			field : 'title',
			title : '标题',
			width : 150,
			align : 'center'
		},

		{
			field : 'description',
			title : '描述',
			width : 150,
			align : 'center',
			formatter : function(val, rec) {
				if (val == null) {
					return "--";
				} else {
					return val;
				}
			}
		},

		{
			field : 'replyText',
			title : '回复文本',
			width : 150,
			align : 'center',
			formatter : function(val, rec) {
				if (val == null) {
					return "--";
				} else {
					return val;
				}
			}
		},

		{
			field : 'replyLink',
			title : '回复链接',
			width : 150,
			align : 'center',
			formatter : function(val, rec) {
				if (val == null) {
					return "--";
				} else {
					return val;
				}
			}
		},

		{
			field : 'replyImg',
			title : '回复图片',
			width : 150,
			align : 'center',
			formatter : function(val, rec) {
				if (val == null) {
					return "--";
				} else {
					return val;
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

function replyTypeEvent() {
	$('#replyTypeAdd').combobox({
		onChange : function(newValue, oldValue) {
			buildFormView(newValue);
		}
	});
}

function buildFormView(type) {
	if (type == 1 || type == 4) {
		// 文本
		$("#replyTextAdd").validatebox('enable');
		$("#descriptionAdd").validatebox('disable');
		$("#replyLinkAdd").validatebox('disable');
		$("#replyImgAdd").validatebox('disable');

		$("#replyTextAdd").closest("tr").show();
		$("#descriptionAdd").closest("tr").hide();
		$("#replyLinkAdd").closest("tr").hide();
		$("#replyImgAdd").closest("tr").hide();
	} else if (type == 2) {
		// 链接
		$("#replyTextAdd").validatebox('disable');
		$("#descriptionAdd").validatebox('enable');
		$("#replyLinkAdd").validatebox('enable');
		$("#replyImgAdd").validatebox('disable');

		$("#replyTextAdd").closest("tr").hide();
		$("#descriptionAdd").closest("tr").show();
		$("#replyLinkAdd").closest("tr").show();
		$("#replyImgAdd").closest("tr").hide();
	} else {
		// 图文
		$("#replyTextAdd").validatebox('disable');
		$("#descriptionAdd").validatebox('enable');
		$("#replyLinkAdd").validatebox('enable');
		$("#replyImgAdd").validatebox('enable');

		$("#replyTextAdd").closest("tr").hide();
		$("#descriptionAdd").closest("tr").show();
		$("#replyLinkAdd").closest("tr").show();
		$("#replyImgAdd").closest("tr").show();
	}
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

	$("#enableAdd").combobox('setValue', 1);
	$("#replyTypeAdd").combobox('enable');

	$("#replyTextAdd").validatebox('enable');
	$("#descriptionAdd").validatebox('enable');
	$("#replyLinkAdd").validatebox('enable');
	$("#replyImgAdd").validatebox('enable');

	$("#replyTextAdd").closest("tr").show();
	$("#descriptionAdd").closest("tr").show();
	$("#replyLinkAdd").closest("tr").show();
	$("#replyImgAdd").closest("tr").show();
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

	$("#addForm").form("load", row);// 直接加在grid数据
	$('#addDialog').dialog('open').dialog('setTitle', '编辑');

	buildFormView(row.replyType);
	$("#replyTypeAdd").combobox('disable');

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
