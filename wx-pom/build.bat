@echo off

::echo ��ǰ�̷���%~d0
::echo ��ǰ�̷���·����%~dp0
::echo ��ǰ�̷���·���Ķ��ļ�����ʽ��%~sdp0
::echo ��ǰ������ȫ·����%~f0
::echo ��ǰCMDĬ��Ŀ¼��%cd%
::��ת����ǰ·�������ǿ����ת����/d����������ת
::cd /d %cd%

@echo ��ȷ����ǰ�����Ѿ�����maven����������
::���maven�汾������callִ�У���������
call mvn -version

::����ȫ��installKey,deployKeyĬ��ֵ
@set "installKey=install"
@set "deployKey=deploy"

:start
::echo=�������
echo=
choice  /C IDC /M "mvn install������I��mvn deploy������D��ȡ��������C"

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
	@echo ִ��%1��ʼ... 
	
	call mvn clean %1 -Dmaven.test.skip=true -Dfile.encoding=UTF-8 -Dmaven.javadoc.skip=false -U -T 1C
	
	ENDLOCAL

	@echo ִ��%1����...
	
	goto:eof

