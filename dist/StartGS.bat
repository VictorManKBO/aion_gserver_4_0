@echo off
color 1f
title Aion-Fly Game Server 4.0 Console

REM Start...
:start
echo Starting Aion-Fly Game Server 4.0 Game Server.
echo.

REM SET PATH="Type here your path to java jdk/jre (including bin folder)."
REM NOTE: Remove tag REM from previous line.

REM -------------------------------------
REM Default parameters for a basic server.
JAVA -Xms512m -Xmx3584m -Xbootclasspath/p:libs/jsr166-1.0.0.jar -ea -javaagent:./libs/al-commons-1.3.jar -cp ./libs/*;AL-Game.jar com.aionemu.gameserver.GameServer
REM -------------------------------------

SET CLASSPATH=%OLDCLASSPATH%

if ERRORLEVEL 2 goto restart
if ERRORLEVEL 1 goto error
if ERRORLEVEL 0 goto end

REM Restart...
:restart
echo.
echo Administrator Restart ...
echo.
goto start

REM Error...
:error
echo.
echo Server terminated abnormaly ...
echo.
goto end

REM End...
:end
echo.
echo Server terminated ...
echo.
pause