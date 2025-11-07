@echo off
echo ========================================
echo 任务看板系统 - 数据库初始化脚本
echo ========================================
echo.

set /p MYSQL_USER="请输入MySQL用户名 (默认root): "
if "%MYSQL_USER%"=="" set MYSQL_USER=root

set /p MYSQL_PASSWORD="请输入MySQL密码: "

echo.
echo 正在连接MySQL并执行初始化脚本...
echo.

cd /d "%~dp0"
mysql -u %MYSQL_USER% -p%MYSQL_PASSWORD% < init.sql

if %ERRORLEVEL% EQU 0 (
    echo.
    echo ========================================
    echo ✅ 数据库初始化成功！
    echo ========================================
    echo.
    echo 数据库名称: task_board
    echo 测试账号:
    echo   用户名: admin / user1 / user2
    echo   密码: 123456
    echo.
) else (
    echo.
    echo ========================================
    echo ❌ 数据库初始化失败！
    echo ========================================
    echo.
    echo 请检查：
    echo 1. MySQL服务是否启动
    echo 2. 用户名和密码是否正确
    echo 3. 是否有创建数据库的权限
    echo.
)

pause
