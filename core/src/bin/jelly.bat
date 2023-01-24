@echo off

@REM Copyright 2002,2004 The Apache Software Foundation.
@REM 
@REM Licensed under the Apache License, Version 2.0 (the "License");
@REM you may not use this file except in compliance with the License.
@REM You may obtain a copy of the License at
@REM 
@REM      http://www.apache.org/licenses/LICENSE-2.0
@REM 
@REM Unless required by applicable law or agreed to in writing, software
@REM distributed under the License is distributed on an "AS IS" BASIS,
@REM WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@REM See the License for the specific language governing permissions and
@REM limitations under the License.
@REM

if "%JELLY_HOME%"=="" goto jelly_home_err
goto run

:jelly_home_err
if "%OS%"=="Windows_NT" SET JELLY_HOME=%~dps0\..
if "%JELLY_HOME%"=="" goto jelly_home_err2
goto run

:jelly_home_err2
echo JELLY_HOME must be specified
goto end

:run
if "%FOREHEAD_CONF%"=="" set FOREHEAD_CONF=%JELLY_HOME%\bin\forehead.conf

"%JAVA_HOME%"\bin\java -classpath "%CLASSPATH%;%JELLY_HOME%\lib\forehead-1.0-beta-5.jar" "-Dforehead.conf.file=%FOREHEAD_CONF%" "-Djelly.home=%JELLY_HOME%" "-Dtools.jar=%JAVA_HOME%\lib\tools.jar" %JELLY_OPTS% com.werken.forehead.Forehead %*

:end
