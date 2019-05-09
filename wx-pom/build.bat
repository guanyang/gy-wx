@echo off

::echo 当前盘符：%~d0
::echo 当前盘符和路径：%~dp0
::echo 当前盘符和路径的短文件名格式：%~sdp0
::echo 当前批处理全路径：%~f0
::echo 当前CMD默认目录：%cd%
::跳转到当前路径，添加强制跳转参数/d，可任意跳转
::cd /d %cd%

@echo 请确保当前机器已经配置maven环境变量！
::检查maven版本，采用call执行，避免闪退
call mvn -version

::设置全局installKey,deployKey默认值
@set "installKey=install"
@set "deployKey=deploy"

:start
::echo=输出空行
echo=
choice  /C IDC /M "mvn install请输入I，mvn deploy请输入D，取消请输入C"

if %errorlevel%==1 (
	call:run %installKey%
	call:start
)
if %errorlevel%==2 (
	call:run %deployKey%
	call:start
)
if %errorlevel%==3 (
	exit
)

:run

SETLOCAL
	@echo 执行%1开始... 
	
	call mvn clean %1 -Dmaven.test.skip=true -Dfile.encoding=UTF-8 -Dmaven.javadoc.skip=false -U -T 1C
	
	ENDLOCAL

	@echo 执行%1结束...
	
	goto:eof

