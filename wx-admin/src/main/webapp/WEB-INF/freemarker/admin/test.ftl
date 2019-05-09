<#import "/WEB-INF/freemarker/common/res_macro.ftl" as resMacro>
<html>
	<head>
		<title>测试</title>
		<@resMacro.jsurl resRoot="${javascriptServer}" url=["/admin/js/base/jquery.md5"] resConcat="${resConcat}" />
	</head>
	<body>
           当前的时间：${time?long}
	</body>
</html>
