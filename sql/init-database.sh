#!/bin/bash
echo "========================================"
echo "任务看板系统 - 数据库初始化脚本"
echo "========================================"
echo ""

# 读取MySQL用户名
read -p "请输入MySQL用户名 (默认root): " MYSQL_USER
MYSQL_USER=${MYSQL_USER:-root}

# 读取MySQL密码
read -sp "请输入MySQL密码: " MYSQL_PASSWORD
echo ""

echo ""
echo "正在连接MySQL并执行初始化脚本..."
echo ""

# 获取脚本所在目录
SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

# 执行SQL脚本
mysql -u "$MYSQL_USER" -p"$MYSQL_PASSWORD" < "$SCRIPT_DIR/init.sql"

if [ $? -eq 0 ]; then
    echo ""
    echo "========================================"
    echo "✅ 数据库初始化成功！"
    echo "========================================"
    echo ""
    echo "数据库名称: task_board"
    echo "测试账号:"
    echo "  用户名: admin / user1 / user2"
    echo "  密码: 123456"
    echo ""
else
    echo ""
    echo "========================================"
    echo "❌ 数据库初始化失败！"
    echo "========================================"
    echo ""
    echo "请检查："
    echo "1. MySQL服务是否启动"
    echo "2. 用户名和密码是否正确"
    echo "3. 是否有创建数据库的权限"
    echo ""
fi
