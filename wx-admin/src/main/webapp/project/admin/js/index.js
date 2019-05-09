$(function() {
	loadTreeMenu();
	tabClose();
	tabCloseEven();
	clockon();
});
function loadTreeMenu() {
	$('#treeMenu').tree({
		// data : data,
		url : "${adminServer}/sysResource/menu.htm?status=1",
		method : 'GET',
		// animate : true,
		fitColumns : true,
		lines : true,
		onClick : function(node) {
			if (node.url != null && node.url != "") {
				var url = node.url;
				// 不带http、https，则默认当做本系统请求
				if (url.indexOf("http://") == -1 && url.indexOf("https://") == -1) {
					url = "${adminServer}" + url;
				}
				addTab(node.text, url);
			}
		},
		onLoadSuccess : function(node, data) {
			collapseAllTreeMenu();
		}
	});
}
// 修改密码窗口
function openPasswordDialog() {
	$('#passwordDialog').dialog('open');
	$('#passwordForm').form('clear');
	$("#passwordForm").form('disableValidation');// 不验证，仅提交的时候验证
}
// 关闭密码窗口
function closePasswordDialog() {
	$('#passwordForm').form('clear');
	$('#passwordDialog').dialog('close');
}

// 修改密码
function savePassword() {
	$("#passwordForm").form('submit', {
		url : "${adminServer}/changePassword.htm",
		onSubmit : function() {
			$("#passwordForm").form('enableValidation');// 验证
			return $("#passwordForm").form('validate');
		},
		success : function(data) {
			var result = eval('(' + data + ')');
			if (result.response && result.response.message) {
				msgShow(result.response.message);
				if (result.response.success) {
					closePasswordDialog();
					setTimeout(goLogout, 2000);
				} else {
					$("#passwordForm").form('disableValidation');// 不验证，仅提交的时候验证
					$('#passwordForm').form('clear');
				}
			} else {
				msgShow("系统异常，请稍后再试！");
			}
		}
	});
}

function clockon() {
	var now = new Date();
	var year = now.getFullYear(); // getFullYear getYear
	var month = now.getMonth();
	var date = now.getDate();
	var day = now.getDay();
	var hour = now.getHours();
	var minu = now.getMinutes();
	var sec = now.getSeconds();
	var week;
	month = month + 1;
	if (month < 10)
		month = "0" + month;
	if (date < 10)
		date = "0" + date;
	if (hour < 10)
		hour = "0" + hour;
	if (minu < 10)
		minu = "0" + minu;
	if (sec < 10)
		sec = "0" + sec;
	var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
	week = arr_week[day];
	var time = "";
	time = year + "年" + month + "月" + date + "日" + " " + hour + ":" + minu + ":" + sec + " " + week;

	$("#bgclock").html(time);

	setTimeout("clockon()", 500);
}
