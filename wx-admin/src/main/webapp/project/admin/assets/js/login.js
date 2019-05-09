$(document).ready(function() {
	loginEvent();
	inputTipEvent();
});

function loginEvent() {
	var $login = $('#loginSubmit');
	// 点击事件
	$login.on('click', function() {
		login();
	});
	// 回车事件
	$(document).keyup(function(e) {
		if (e.keyCode == 13) {
			// 此处编写用户敲回车后的代码
			login();
		}
	});
}

function inputTipEvent() {
	$('.page-container .username, .page-container .password').keyup(function() {
		$(this).parent().find('.error').fadeOut('fast');
	});
}

function login() {
	var $container = $('.page-container');
	var username = $container.find('.username').val();
	if (username == '') {
		$container.find('.error').fadeOut('fast', function() {
			$(this).css('top', '27px');
		});
		$container.find('.error').fadeIn('fast', function() {
			$container.find('.username').focus();
		});
		return;
	}
	var password = $container.find('.password').val();
	if (password == '') {
		$container.find('.error').fadeOut('fast', function() {
			$(this).css('top', '96px');
		});
		$container.find('.error').fadeIn('fast', function() {
			$container.find('.password').focus();
		});
		return;
	}

	$.ajax({
		url : "${adminServer}/login.htm",
		type : 'POST',
		cache : false,
		data : {
			username : username,
			password : password
		},
		error : function(e) {
			loginTip(false, "网络异常，请稍后重试！");
		},
		success : function(response) {
			if (!!response) {
				if (!response.success) {
					loginTip(false, response.message);
				} else {
					loginTip(true, "登录成功");
					setTimeout(function() {
						// 跳转到登录之后的默认页面
						var url = "${adminServer}" + response.data.targetUrl;
						window.location.href = url;
					}, 500);
				}
			} else {
				loginTip(false, "登录异常，请稍后重试！");
			}
		}
	});
}

function loginTip(success, message) {
	var $tip = $("#loginTip");
	if (success) {
		$tip.html(message || "登录成功");
		$tip.css("color", "green");
	} else {
		$tip.html(message || "登录异常，请稍后重试！");
		$tip.css("color", "red");
	}
}