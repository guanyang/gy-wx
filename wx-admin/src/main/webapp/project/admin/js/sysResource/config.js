var url = "${adminServer}/sysResource/menu.htm";
var saveUrl = "${adminServer}/sysResource/save.htm";
var delUrl = "${adminServer}/sysResource/del.htm";
var editingId;// 当前编辑行id
var standardStatus = [ {
	"value" : "0",
	"text" : "未启用"
}, {
	"value" : "1",
	"text" : "启用"
} ];
tdSize();// 必须放在外面
$(document).ready(function() {
	init();
});
function getQueryParams() {
	var obj = {
		name : $.trim($('#name').val()),
		status : $.trim($('#status').val())
	};
	return obj;
}
function init() {
	$('#tt').treegrid({
		method : 'GET',
		singleSelect : true,
		striped : true,
		rownumbers : "true",
		// pagination : "true",
		width : "100%",
		height : 400,
		idField : 'id',
		treeField : 'text',
		// animate : true,
		fitColumns : true,
		// lines : true,
		queryParams : getQueryParams(),
		url : url,
		toolbar : '#tbMenus',
		columns : [ [ {
			field : 'id',
			title : '节点ID',
			width : 100,
			align : 'left'

		}, {
			field : 'text',
			title : '节点名称',
			width : 220,
			align : 'left',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : [ 'length[2,20]' ]
				}
			}
		}, {
			field : 'parentId',
			title : '父节点ID',
			width : 100,
			align : 'left'

		}, {
			field : 'url',
			title : 'URL链接',
			width : 300,
			align : 'left',
			editor : {
				type : 'validatebox',
				options : {}
			}
		}, {
			field : 'description',
			title : '节点描述',
			width : 150,
			align : 'left',
			editor : {
				type : 'validatebox',
				options : {
					required : true,
					validType : [ 'length[2,50]' ]
				}
			}
		}, {
			field : 'status',
			title : '是否启用',
			width : 150,
			align : 'left',
			formatter : function(val, rec) {
				if (val == 0) {
					return '未启用';
				} else if (val == 1) {
					return '启用';
				}
			},
			editor : {
				type : 'combobox',
				options : {
					required : true,
					editable : false,
					data : standardStatus,
					valueField : "value",
					textField : "text",
					panelHeight : "auto"
				}
			}
		} ] ],
		onDblClickRow : function(row) {
			cancelEdit();// 取消之前的编辑
			addEdit(row.id);// 添加新的编辑
		},
		onAfterEdit : function(row, changes) {
			// console.log(row);
			// console.log(changes);

		},
		onLoadSuccess : function(row, data) {
			collapseAll();
		}
	});
}

/**
 * 折叠
 * @Author gy
 * @Date 2016年8月8日上午1:40:25
 */
function collapseAll(){
	$('#tt').treegrid("collapseAll");
}
/**
 * 展开
 * @Author gy
 * @Date 2016年8月8日上午1:41:09
 */
function expandAll(){
	$('#tt').treegrid("expandAll");
}

/**
 * 添加
 * 
 * @param flag
 *            标志，true表示添加子节点，false表示添加一级节点
 */
function add(flag) {
	var parendId = null;
	cancelEdit();// 取消之前的编辑
	if (flag) {
		var row = $("#tt").treegrid("getSelected");
		if (!row) {
			msgShow("请选择对应的父节点！");
			return;
		}
		parendId = row.id;
	}
	var tmpId = -1;
	$("#tt").treegrid('append', {
		parent : (parendId ? parendId : null),
		data : [ {
			id : tmpId,
			parentId : parendId ? parendId : -1,
			status : 1
		} ]
	});
	addEdit(tmpId);// 添加新的编辑

}

/**
 * 保存
 */
function save() {
	if (editingId != undefined) {
		var flag = $("#tt").treegrid("validateRow", editingId);
		if (!flag) {
			msgShow("请完善配置信息！");
			return;
		}
		var row = $("#tt").treegrid("getSelected");
		$('#tt').treegrid('endEdit', editingId);
		$.ajax({
			url : saveUrl,
			type : "POST",
			cache : false,
			data : {
				id : row.id,
				name : row.text,
				parentId : row.parentId,
				url : row.url,
				description : row.description,
				status : row.status
			},
			error : function(e) {
				msgShow("系统异常，请稍后再试！");
			},
			success : function(data) {
				if (data.response && data.response.message) {
					msgShow(data.response.message);
					if (data.response.success) {
						doSearch();// 刷新页面
					}
				} else {
					msgShow("系统异常，请稍后再试！");
				}
			}
		});
	} else {
		msgShow("目前没有可编辑的行！");
	}
}
/**
 * 编辑
 */
function edit() {
	var row = $("#tt").treegrid("getSelected");
	if (!row) {
		msgShow("请选择要操作的行！");
		return;
	} else {
		cancelEdit();// 取消之前的编辑
		addEdit(row.id);// 添加新的编辑
	}
}

/**
 * 删除
 */
function del() {
	var row = $("#tt").treegrid("getSelected");
	if (!row) {
		msgShow("请选择要操作的行！");
		return;
	}
	cancelEdit();
	var children = $("#tt").treegrid("getChildren", row.id);
	var idArr = [];
	idArr.push(row.id);// 添加本节点
	for (var i = 0; i < children.length; i++) {
		idArr.push(children[i].id);// 添加子节点
	}
	$.messager.confirm("提示信息", '您确定要删除<strong style="color:red">该节点及所有子节点一共' + idArr.length + '条</strong>记录吗？', function(r) {
		if (r) {
			$.ajax({
				url : delUrl,
				type : "POST",
				cache : false,
				data : {
					ids : idArr.join(",")
				},
				error : function(e) {
					msgShow("系统异常，请稍后再试！");
				},
				success : function(data) {
					if (data.response && data.response.message) {
						msgShow(data.response.message);
						if (data.response.success) {
							doSearch();// 刷新页面
						}
					} else {
						msgShow("系统异常，请稍后再试！");
					}
				}
			});
		}
	});
}
/**
 * 取消
 */
function cancel() {
	if (editingId != undefined) {
		cancelEdit();
	} else {
		msgShow("目前没有可编辑的行！");
	}
}
/**
 * 查询
 */
function doSearch() {
	cancelEdit();
	// 避免重新初始化grid
	$("#tt").treegrid("options").url = url;
	$("#tt").treegrid("reload", getQueryParams());
}
/**
 * 重新加载
 */
function reload() {
	cancelEdit();
	$("#tt").treegrid('reload');
}

/**
 * 重置查询条件
 */
function reset() {
	$('#searchForm').form('clear');
}

/**
 * 取消行编辑状态
 */
function cancelEdit() {
	if (editingId != undefined) {
		$('#tt').treegrid('cancelEdit', editingId);
		if (editingId == -1) {
			$('#tt').treegrid('remove', editingId);
		}
		editingId = undefined;
	}
}
/**
 * 添加行编辑状态
 */
function addEdit(id) {
	editingId = id;
	$('#tt').treegrid('beginEdit', editingId);
	$('#tt').treegrid('select', editingId);
}
