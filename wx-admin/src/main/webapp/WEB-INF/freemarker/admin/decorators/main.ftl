<#setting url_escaping_charset='utf-8'>
﻿<#import "/WEB-INF/freemarker/common/res_macro.ftl" as resMacro>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="zh-CN">
	<head>
		<title>${title}</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<meta http-equiv="Cache-Control" content="no-transform" />
		<meta name="applicable-device" content="pc">
		<link rel="shortcut icon" href="//www.guanyang.org/favicon.ico" type="image/x-icon">
		<#include "/WEB-INF/freemarker/common/jquery-easyui.ftl">
		${head}
	</head>
	<body>
        ${body}
	</body>
</html>
