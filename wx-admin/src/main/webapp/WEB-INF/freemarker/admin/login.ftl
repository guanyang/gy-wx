<#import "/WEB-INF/freemarker/common/res_macro.ftl" as resMacro>
<!DOCTYPE html>
<html lang="en" class="no-js">
    <head>
        <title>后台管理系统</title>
        <@resMacro.cssurl resRoot="${cssServerAdmin}/assets/css" url=["/reset","/supersized","/style"] resConcat="${resConcat}" />
    </head>
    <body>

        <div class="page-container">
            <h1>Welcome to sign in</h1>
            <#--<form action="${adminServer}/login.htm" method="post">-->
            <div class="login-container">
                <input type="text" name="username" class="username" placeholder="用户名">
                <input type="password" name="password" class="password" placeholder="密码">
                <button id="loginSubmit" type="submit">提交</button>
                <div class="error"><span>+</span></div>
            </div>    
            <#--</form>-->
            <div class="connect">
            	<p id="loginTip">Copyright© 2015-2018</p>
                <#--<p>
                    <a class="facebook" href=""></a>
                    <a class="twitter" href=""></a>
                </p>-->
            </div>
        </div>
		
        <@resMacro.jsurl resRoot="${javascriptServerAdmin}" url=["/js/base/jquery-1.8.2.min","/assets/js/supersized.3.2.7.min","/assets/js/supersized-init","/assets/js/login"] resConcat="${resConcat}" />

    </body>

</html>


