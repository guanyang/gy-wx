// ie判断
var ie = !+[ 1, ];

/**
 * 查询页面的重新计算
 * 
 * @author 韩亮
 */
function tdSize() {
	var scrreenW = screen.width;
	var $outerTable = $(".tblContent");
	var $tdl = $outerTable.find("td.tdLeft");
	var $td = $outerTable.find("td.tdRight");
	var $tdInput = $td.children("input");
	var $to = $outerTable.find(".to");
	var tdLW = 100;
	var tdW;
	if($outerTable.attr("name")=="three"){
		$outerTable.width(scrreenW - 180 - 150 - 100);
		tdW = $outerTable.width() / 3 - tdLW; 
	}else{
		$outerTable.width(scrreenW - 180 - 100);
		tdW = $outerTable.width() / 4 - tdLW; 
	}

	$td.css({
		width : tdW
	});
	$tdl.css({
		width : tdLW
	});
	$tdInput.css({
		width : tdW
	});
	$(".datebox").datetimebox({
		width : tdW,
		panelWidth : tdW
	});
	$td.find("select").css({
		width : tdW
	});
}

/**
 * @author 韩亮
 * 添加编辑页面样式，添加项过多，出现滚动条的情况   
 * 1：仅仅让添加项出现滚动条
 * 2：保存取消按钮浮动在下方
 * 
 * 使用方法：
 * <div class="grid-toolbar" id="scroll" style="overflow-y:auto; overflow-x:hidden; ">---添加项所在div 
 * 1：id为scroll
 * 2：添加style="overflow-y:auto; overflow-x:hidden; "样式
 * 3：将divSize() 方法写在初始化加载函数中
 */
function divSize(){
	var scollH=$(window).height()-100;
	$("#scroll").height(scollH);
}

/*
 * 检测对象是否是空对象(不包含任何可读属性)。 //如你上面的那个对象就是不含任何可读属性 方法只既检测对象本身的属性，不检测从原型继承的属性。
 */
function isOwnEmpty(obj) {
	for ( var name in obj) {
		if (obj.hasOwnProperty(name)) {
			return false;
		}
	}
	return true;
}

/*
 * 检测对象是否是空对象(不包含任何可读属性)。 方法既检测对象本身的属性，也检测从原型继承的属性(因此没有使hasOwnProperty)。
 */
function isEmpty(obj) {
	for ( var name in obj) {
		return false;
	}
	return true;
}

/**
 * form.reset()
 * 会清空所有的样式为combo-value的时间控件的值，如果在当前页有添加或者编辑等功能用此时间控件，需要调整之后才能用
 */
function reset(frm) {
	if (getType(frm).toLowerCase().indexOf("form") > -1) {
		frm.reset();
		// datetime clean
		$(".combo-value").attr('value', '');
	}
	return false;
}

/**
 * 数组转JSON
 * 
 * @param o
 * @returns
 */
function arrayToJson(o) {
	var r = [];
	if (typeof o == "string")
		return "\""
				+ o.replace(/([\'\"\\])/g, "\\$1").replace(/(\n)/g, "\\n")
						.replace(/(\r)/g, "\\r").replace(/(\t)/g, "\\t") + "\"";
	if (typeof o == "object") {
		if (!o.sort) {
			for ( var i in o)
				r.push(i + ":" + arrayToJson(o[i]));
			if (!!document.all
					&& !/^\n?function\s*toString\(\)\s*\{\n?\s*\[native code\]\n?\s*\}\n?\s*$/
							.test(o.toString)) {
				r.push("toString:" + o.toString.toString());
			}
			r = "{" + r.join() + "}";
		} else {
			for ( var i = 0; i < o.length; i++) {
				r.push(arrayToJson(o[i]));
			}
			r = "[" + r.join() + "]";
		}
		return r;
	}
	return o.toString();
}

/**
 * firefox取node的下一个兄弟元素节点
 */
function nextElementSibling(node) {
	if (node.nextElementSibling != undefined) {
		return node.nextElementSibling;
	} else {
		return null;
	}
}

/**
 * IE取node的下一个兄弟元素节点
 */
function nextSibling(node) {
	do {
		if (node != null) {
			node = node.nextSibling;
		}
	} while (node != null && node.nodeType != 1) // 1:html node; 3:text node
	return node;
}

/**
 * 一般是验证输入文本框，在文本框后面动态加入span标记，验证失败的消息显示在里面。兼容IE和Firefox
 * 
 * @param {Object}
 *            obj 被验证的对象
 * @param {Object}
 *            reg 正则表达式
 * @param {Object}
 *            msg 消息
 * @param {Object}
 *            isEmpty 验证失败后 obj 是否获取焦点
 * @author niusg 原创
 * @date 2013-05-07
 */
function regData(obj, reg, msg, isEmpty) {
	var nextNode;
	var span;
	if (ie) {
		nextNode = nextSibling(obj);
	} else {
		nextNode = nextElementSibling(obj);
	}
	if (nextNode != null) {
		// if((nextNode.tagName != 'undefined' && nextNode.tagName.toLowerCase()
		// == 'span')
		// || nextNode.getElementsByTagName("span") != 'undefined'){
		span = nextNode;
	} else {
		span = document.createElement("span");
		obj.parentNode.appendChild(span);
	}
	if (!isEmpty && obj.value.trim() == '') {
		span.innerHTML = "";
		span.setAttribute("name", "");
		return true;
	}
	if (!reg.test(obj.value.trim())) {
		span.setAttribute("style", "font-size:12px;color:#ff0000;");
		span.setAttribute("name", "spv_err");
		span.innerHTML = msg;
		if (!isEmpty) {
			obj.focus();
		}
		return false;
	} else {
		span.setAttribute("style",
				"font-size:18px;color:#00ff00;font-weight:900");
		span.setAttribute("name", "spv_ok");
		span.innerHTML = "√";
		return true;
	}
}

/**
 * 一般是验证输入文本框，在文本框后面动态加入span标记，验证失败的消息显示在里面。兼容IE和Firefox
 * 
 * @param {Object}
 *            obj 被验证的对象
 * @param {Object}
 *            msg 消息
 * @author niusg 原创
 * @date 2013-09-22
 */
function objData(obj, msg) {
	var nextNode;
	var span;
	if (ie) {
		nextNode = nextSibling(obj);
	} else {
		nextNode = nextElementSibling(obj);
	}
	if (nextNode != null) {
		span = nextNode;
	} else {
		span = document.createElement("span");
		obj.parentNode.appendChild(span);
	}
	if (msg != null && msg != '') {
		span.setAttribute("style", "font-size:12px;color:#ff0000;");
		span.setAttribute("name", "spv_err");
		span.innerHTML = msg;
		if (!isEmpty) {
			obj.focus();
		}
		return false;
	} else {
		span.setAttribute("style",
				"font-size:18px;color:#00ff00;font-weight:900");
		span.setAttribute("name", "spv_ok");
		span.innerHTML = "√";
		return true;
	}
}

/**
 * 检查是否有错误消息，如果有，返回true。
 */
function hasSpanError(frm) {
	if (frm) {
		$(frm)
				.find(
						"input[type='text']:visible,input[type='password'],textarea,select")
				.each(function(i) {
					if (i < 20) {
						$(this).blur();
					}
				});
	}
	;
	return $("span[name='spv_err']").is("span");
}

/**
 * 成功提交后的返回消息
 */
function afterSuccess(data) {
	if (data.sessionTimeout) { // 登录超时
		window.location.href = getBasePath() + "rest/login/logout";
	}
	if (data.tokenMessage) { // 重复提交信息
		msgShow(data.tokenMessage, "重复提交信息");
		return false;
	}
	var msg = validationErrors(data.validationErrors);
	if (msg != "") {
		msgShow(msg, "后台验证错误信息");
		return false
	}
	if (data.message) {
		msgShow(data.message, "提示信息");
	}
	if (data.errors) {
		msgShow(data.errors, "错误信息");
	}
	return true;
}

/**
 * 不能为空
 */
function regNotEmpty(obj) {
	var reg = /\S+/; // 匹配不全部是"空"
	var msg = "不能为空";
	return regData(obj, reg, msg, true);
}

/**
 * 是否阿拉伯数字
 * 
 * @param {Object}
 *            obj
 */
function regDigit(obj) {
	var reg = /^[0-9]*$/;
	var msg = "请输入阿拉伯数字";
	return regData(obj, reg, msg);
}

function regTelephone(obj) {
	var reg = /^(?:(\d{2,5}-)?(\d{7,10}))?$/;
	var msg = "请输入7至10位数字,如果有区号，后面使用-分割";
	return regData(obj, reg, msg, false);
}

function regMobile(obj) {
	var reg = /^1\d{10}$/;
	var msg = "手机格式不对";
	return regData(obj, reg, msg, false);
}

/**
 * 邮箱验证
 */
function regEmail(obj) {
	var reg = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
	var msg = "邮箱格式不正确";
	return regData(obj, reg, msg);
}

/**
 * 最大长度验证
 * 
 * @param obj
 *            目标对象
 * @param maxLen
 *            最大长度：适用于一个汉字等于两个字符的判断
 * @returns true: 小于最大长度,验证成功
 */
function regMaxLength(obj, maxLen) {
	var len = strlength(obj.value);
	if (len > maxLen) {
		return regDateFlag(obj,
				"不能超过" + (maxLen / 2) + "个汉字或" + maxLen + "个字符", false);
	} else {
		return regDateFlag(obj, "", true);
	}
}

/**
 * 指定 “希望” 的结果
 * 
 * @param obj
 *            待验证的对象
 * @param msg
 *            错误后的提示消息
 * @param flag
 *            指定的结果, false:验证失败，会有提示信息; true :验证成功,不会有提示信息
 */
function regDateFlag(obj, msg, flag) {
	if (flag == true) {
		// 表达式 "任意",永远都匹配 return true
		return regData(obj, /./, "", true);
	} else {
		// 表达式 "还未开始就已经结束",永远都不会匹配 return false
		return regData(obj, /^$/, msg, true);
	}
}

/**
 * 根据时间戳生成ID，需要控制在整型范围内。
 */
function getTimeIntId() {
	var tm = new Date();
	var tid = new String(tm.getTime());
	tid = tid.right(8);
	tid = Math.abs(parseInt(tid));
	return tid;
}

// easy ui datatimebox时间格式
function dataTimeBoxFormat(time) {
	var t = new Date(time);
	var tf = function(i) {
		return (i < 10 ? '0' : '') + i
	};
	return 'yyyy-MM-dd HH:mm:ss'.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
		switch (a) {
		case 'yyyy':
			return tf(t.getFullYear());
			break;
		case 'MM':
			return tf(t.getMonth() + 1);
			break;
		case 'mm':
			return tf(t.getMinutes());
			break;
		case 'dd':
			return tf(t.getDate());
			break;
		case 'HH':
			return tf(t.getHours());
			break;
		case 'ss':
			return tf(t.getSeconds());
			break;
		}
	})
}

/**
 * 从easyUI 的datetimebox 组件中获得时间
 * 
 * @timeValue 控件值格式为yyyy-MM-dd HH:mm:ss
 * @return 返回date对象
 */
function date_from_dateTimeBox(timeValue) {
	var dt = [];
	timeValue.replace(/\d{1,4}/g, function(val) {
		dt[dt.length] = val;
	});
	return new Date(dt[0], dt[1] - 1, dt[2], dt[3], dt[4], dt[5]).getTime();// 获得时间值
}

/**
 * 公共消息 提示
 * 
 * @param msg
 *            提示内容
 * @param title
 *            弹出框 的title
 */
function msgShow(msg, title) {
	$.messager.show({
		title : title || "提示信息",
		msg : msg,
		timeout : 2000,
		showType : 'slide'
	});
}


/**
 * 实现在dataGrid中鼠标移到所在列时，有悬浮的提示信息 来显示列的内容
 * 
 * @param value
 * @param row
 * @param index
 * @returns 带有title(提示信息)的span
 */
function showValueAndTitle(value, row, index) {
	if(!value){
		return "";
	}
	return '<span title=' + "'" + value + "'" + '>' + value + '</span>';
}

/**
 * 显示最大化窗体
 * 
 * @param data
 *            扩展属性
 */
function showMaxWindow(data) {
	var dt = {
		"maximized" : true, // 所有边界组件数据信息的窗体 全屏/最大化显示
		"minimizable" : false,// 隐藏 最小化的按钮
		"maximizable" : false,// 隐藏 最大化的按钮
		"collapsible" : false,// 隐藏 收缩的按钮
		"draggable" : false
	// 禁止窗体拖拽
	};
	dt = $.extend(dt, data); // 扩展自定义 属性
	$(this).window(dt).window("open");
}

/**
 * 
 * 选中dataGrid中 要移动的项进行移动
 * 
 * @param direction
 *            移动的方向: up:向上移; down:向下移;
 * @param target
 *            目标对象
 */
function dataGridMove(direction, target) {
	// 获取选中的行中的第一行
	var row = $(target).datagrid('getSelected');
	if (!row) {// 没有选中行
		msgShow("请选择要移动的行");
		return;
	}
	// 获得 行的索引
	var rowIndex = $(target).datagrid('getRowIndex', row);

	if (direction == "up") {
		if (rowIndex == 0) {
			msgShow("已是第一行了,不能继续往上了");
			return;
		}
		// 新添加一行数据(数据值:为选中的行的数据值)
		$(target).datagrid('insertRow', {
			index : rowIndex - 1,
			row : row
		});
		// 删除选中的行
		$(target).datagrid('deleteRow', rowIndex + 1);
		// 重新选中 新添加的行
		$(target).datagrid('selectRow', rowIndex - 1);

	} else if (direction == "down") {
		var dataLength = $(target).datagrid('getRows').length - 1;
		if (rowIndex == dataLength) {
			msgShow("已是最后一行了,不能再低了");
			return;
		}
		$(target).datagrid('insertRow', {
			index : rowIndex + 2,
			row : row
		});
		$(target).datagrid('deleteRow', rowIndex);
		$(target).datagrid('selectRow', rowIndex + 1);

	}

}

/**
 * 隐藏 页面初始化时,easyui组件 自动验证的提示信息
 */
function hideValidate() {
	$(".validatebox-invalid").removeClass("validatebox-invalid");// 删除验证提示
}

/**
 * 取得应用的完整路径
 */
function getBasePath() {
	var url = window.location.href;
	index = url.indexOf("/imps-admin-web");
	if (index < 0) {
		return "/";
	}
	return url.substring(0, index + 1);
}
/**
 * 算字符串长度 一个中文 全角是2个字符
 * 
 * @13011146
 */
function strlength(str) {
	if (str == '' || str == null || typeof (str) == 'undefined') {
		return 0;
	}
	var a = $.trim(str).length;
	for ( var i = 0; i < a; i++) {
		if (str.charCodeAt(i) > 127) {
			a++;
		}
	}
	return a;
}

/**
 * 判断一段时间是否是合法 后面时间是否大于前面时间
 * 
 * @13011146
 */
/**
 * 时间检查
 */
function checkDateTime(d1, d2) {
	var t1, t2, d1_new, d2_new;
	var d1_array = d1.substring(0, 10).split('-');
	var d2_array = d2.substring(0, 10).split('-');
	d1_new = d1_array[1] + '/' + d1_array[2] + '/' + d1_array[0] + ' '
			+ d1.substring(10, 19);
	d2_new = d2_array[1] + '/' + d2_array[2] + '/' + d2_array[0] + ' '
			+ d2.substring(10, 19);
	t1 = Date.parse(d1_new);
	t2 = Date.parse(d2_new);

	if (d1 != null && d1 != '' && (isNaN(t1) || d1.length != 19)) {
		$.messager.show({
			title : '提示',
			msg : '起始时间不合法，请重新选择！',
			showType : 'fade',
			timeout : 3000,
			height : 150
		});
		return false;
	}
	if (d2 != null && d2 != '' && (isNaN(t2) || d2.length != 19)) {
		$.messager.show({
			title : '提示',
			msg : '结束时间不合法，请重新选择！',
			showType : 'fade',
			timeout : 3000,
			height : 150
		});
		return false;
	}
	if (d1 != null && d1 != '' && d2 != null && d2 != '') {
		if (t1 > t2) {
			$.messager.show({
				title : '提示',
				msg : '起始时间不能大于结束时间，请重新选择！',
				showType : 'fade',
				timeout : 3000,
				height : 150
			});
			return false;
		}
	}
	return true;
}

/**
 * 判断一个时间是否是合法 是否小于当前时间
 * 
 * @13011146
 */
function checkDateSingle(d1, d2) {

	var t1, t2, d1_new, d2_new;
	var d1_array = d1.substring(0, 10).split('-');
	var d2_array = d2.substring(0, 10).split('-');
	d1_new = d1_array[1] + '/' + d1_array[2] + '/' + d1_array[0] + ' '
			+ d1.substring(10, 19);
	d2_new = d2_array[1] + '/' + d2_array[2] + '/' + d2_array[0] + ' '
			+ d2.substring(10, 19);
	t1 = Date.parse(d1_new);
	t2 = Date.parse(d2_new);
	if (isNaN(t1)) {
		msgShow('时间不合法，请重新选择！', null);
		return false;
	}
	if (t1 < t2) {
		msgShow("起始时间必须大于当前时间", null);
		return false;
	}
	
	return true;
}

/**
 * 手机格式校验
 * 
 * @13011146
 */
function isphone(s) {
	var patrn = /^1\d{10}$/;
	if (!patrn.test(s)) {
		msgShow('手机格式不合法', null);
		return false;
	}
	return true;
}
/**
 * 座机格式校验
 * 
 * @13011146
 */
function ishomephone(s) {
	var re = /^(?:(\d{2,5}-)?(\d{7,10}))?$/;
	if (!re.test(s)) {
		msgShow("请输入固定电话,如 区号-电话", null);
		return false;
	}
	return true;
}

/**
 * 将毫秒时间转为 yyyy-MM-dd HH:mm:ss日期格式
 * 
 * @13011146
 */
var formattime = function(time) {
	var format = 'yyyy-MM-dd HH:mm:ss';
	if (time == null || time == "") {
		return null;
	}
	var t = new Date(time);
	var tf = function(i) {
		return (i < 10 ? '0' : '') + i
	};
	return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
		switch (a) {
		case 'yyyy':
			return tf(t.getFullYear());
			break;
		case 'MM':
			return tf(t.getMonth() + 1);
			break;
		case 'mm':
			return tf(t.getMinutes());
			break;
		case 'dd':
			return tf(t.getDate());
			break;
		case 'HH':
			return tf(t.getHours());
			break;
		case 'ss':
			return tf(t.getSeconds());
			break;
		}
	})

}

/**
 * Spring validation errors join to string
 * 
 * @author niusg
 */
function validationErrors(errors) {
	if (errors) {
		errors = eval(errors);
	} else {
		return "";
	}
	if (errors.defaultMessage) {
		return errors.defaultMessage;
	}
	var msg = "";
	if (errors.length && errors.length > 0) {
		for ( var i in errors) {
			msg += errors[i].defaultMessage + ";";
		}
	}
	return msg;
}

/**
 * 从数据集合中获得指定字段field、指定值value在集合中第一次出现的索引, start from zero 用于页面表单的数据编辑、删除
 * 
 * rowsData json数据集合 field 字段名 value 指定值
 * 
 * @return 指定数据在rowData中的索引
 */
function getIndexOfRows(rowsData, field, value) {
	if (!rowsData || rowsData.length == 0) {// 集合的数据为空
		return null;
	}
	field = "" + field;
	if (typeof rowsData[0][field] == "undefined") { // 字段无效：不存在
		return rowsData[0][field];
	}
	var len = rowsData.length;
	for ( var i = 0; i < len; i++) {
		if (rowsData[i][field] == value) {
			return i;
		}
	}
}

/**
 * 宝宝身长验证 孕周概述使用
 * 
 * @author anonymous
 */
function babyHeightUnborn(obj) {
	var reg = /^([1-5])?\d(\.\d{1,2})?$|^60(\.0{1,2})?$/;
	var msg = "0-60之间,小数点后两位";
	return regData(obj, reg, msg, true);
}

/**
 * 其他验证 未出生"孕妇体重平均增加",出生后"宝宝平均体重","宝宝平均身长"
 */
function otherFiveLength(obj) {
	var reg = /^(0|[1-9]\d*)(\.\d{1,2})?$/;
	var msg = "格式错误";
	return regData(obj, reg, msg, true);
}


function checkOverNum(obj){
	if(!otherFiveLength(obj)){
		return false;
	}
	var len=strlength(obj.value);
	if(len>5){
		return regData(obj,/^$/,"5个字符以内",true);
	}else{
		return regData(obj,/./,"5个字符以内",true);
	}
}


/**
 * 判断长度 返回bolean型 王海霞
 */
function checkLen(obj, maxLen) {
	var len = strlength(obj.value);
	if (len > maxLen) {
		return false;
	} else {
		return true;
	}
}

//截取前10位
function stripHTML(value){
var reTag = /<(?:.|\s)*?>/g;
var reBank=/\s/ig;
return value.replace(reTag,"").replace(reBank,"").replace("&nbsp;","");
}

/**
 * 是否阿拉伯数字
 */
function _isDigit(s) {
	var re = /^\d*$/;
	return re.test(s);
}
/**
*验证非空字符串20141020
**/
function  regNotNull(str){
	if(str == null){
		return false;
	}
	if((str+'').trim() == ""){
		return false;
	}
	return true;
}

Array.prototype.remove = function(val) {
	 var index = this.indexOf(val);
	 if (index > -1) {
	 this.splice(index, 1);
}
};