# 开发环境配置指南

本文档详细说明如何配置项目开发环境。

---

## 📋 目录

- [环境要求](#环境要求)
- [Java环境配置](#java环境配置)
- [Node.js环境配置](#nodejs环境配置)
- [数据库配置](#数据库配置)
- [Redis配置](#redis配置)
- [IDE配置](#ide配置)
- [常见问题](#常见问题)

---

## 🎯 环境要求

| 软件 | 版本要求 | 说明 |
|------|---------|------|
| Java | 17+ | 后端运行环境 |
| Node.js | 18+ | 前端运行环境 |
| Maven | 3.8+ | Java项目构建工具 |
| MySQL | 8.0+ | 主数据库 |
| Redis | 7.0+ | 缓存数据库 |
| Git | 最新版 | 版本控制工具 |

---

## ☕ Java环境配置

### 1. 安装JDK 17

#### Windows
1. 下载JDK：https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
2. 运行安装程序，选择安装路径（如：`C:\Program Files\Java\jdk-17`）
3. 配置环境变量：
   ```
   JAVA_HOME = C:\Program Files\Java\jdk-17
   Path 添加 %JAVA_HOME%\bin
   ```
4. 验证安装：
   ```bash
   java -version
   javac -version
   ```

#### macOS
```bash
# 使用Homebrew安装
brew install openjdk@17

# 配置环境变量（添加到 ~/.zshrc 或 ~/.bash_profile）
export JAVA_HOME=$(/usr/libexec/java_home -v 17)
export PATH=$JAVA_HOME/bin:$PATH

# 重新加载配置
source ~/.zshrc

# 验证安装
java -version
```

#### Linux (Ubuntu/Debian)
```bash
# 安装OpenJDK 17
sudo apt update
sudo apt install openjdk-17-jdk

# 验证安装
java -version
javac -version
```

### 2. 安装Maven

#### Windows
1. 下载Maven：https://maven.apache.org/download.cgi
2. 解压到目录（如：`C:\Program Files\Apache\maven`）
3. 配置环境变量：
   ```
   MAVEN_HOME = C:\Program Files\Apache\maven
   Path 添加 %MAVEN_HOME%\bin
   ```
4. 验证安装：
   ```bash
   mvn -version
   ```

#### macOS/Linux
```bash
# macOS使用Homebrew
brew install maven

# Linux使用包管理器
sudo apt install maven

# 验证安装
mvn -version
```

### 3. 配置Maven镜像（可选，加速依赖下载）

编辑 `~/.m2/settings.xml`（Windows: `C:\Users\{用户名}\.m2\settings.xml`）：

```xml
<settings>
  <mirrors>
    <mirror>
      <id>aliyun</id>
      <mirrorOf>central</mirrorOf>
      <name>Aliyun Maven</name>
      <url>https://maven.aliyun.com/repository/public</url>
    </mirror>
  </mirrors>
</settings>
```

---

## 🟢 Node.js环境配置

### 1. 安装Node.js

#### Windows/macOS
下载安装包：https://nodejs.org/
- 推荐下载LTS版本（18.x或20.x）
- 运行安装程序，默认会同时安装npm

#### Linux
```bash
# 使用NodeSource安装最新LTS版本
curl -fsSL https://deb.nodesource.com/setup_lts.x | sudo -E bash -
sudo apt-get install -y nodejs

# 验证安装
node -v
npm -v
```

### 2. 配置npm镜像（可选，加速包下载）

```bash
# 使用淘宝镜像
npm config set registry https://registry.npmmirror.com

# 或者使用cnpm
npm install -g cnpm --registry=https://registry.npmmirror.com

# 验证配置
npm config get registry
```

### 3. 安装pnpm（可选，更快的包管理器）

```bash
npm install -g pnpm

# 使用pnpm代替npm
pnpm install
pnpm run dev
```

---

## 🗄️ 数据库配置

### 1. 安装MySQL 8.0

#### Windows
1. 下载MySQL安装包：https://dev.mysql.com/downloads/mysql/
2. 运行安装程序
3. 配置root密码
4. 启动MySQL服务

#### macOS
```bash
# 使用Homebrew安装
brew install mysql@8.0

# 启动MySQL服务
brew services start mysql@8.0

# 初始化安全配置
mysql_secure_installation
```

#### Linux
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install mysql-server

# 启动MySQL服务
sudo systemctl start mysql
sudo systemctl enable mysql

# 初始化安全配置
sudo mysql_secure_installation
```

### 2. 创建数据库

```bash
# 登录MySQL
mysql -u root -p

# 执行项目SQL脚本
source /path/to/task-board-system/sql/init.sql

# 或者在MySQL命令行中执行
CREATE DATABASE task_board CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

### 3. 创建数据库用户（可选，推荐）

```sql
-- 创建专用用户
CREATE USER 'taskboard'@'localhost' IDENTIFIED BY 'your_password';

-- 授权
GRANT ALL PRIVILEGES ON task_board.* TO 'taskboard'@'localhost';

-- 刷新权限
FLUSH PRIVILEGES;
```

### 4. 修改后端配置

编辑 `backend/src/main/resources/application.yml`：

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/task_board?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Shanghai
    username: root  # 或者 taskboard
    password: your_password  # 修改为你的密码
```

---

## 🔴 Redis配置

### 1. 安装Redis

#### Windows
1. 下载Redis for Windows：https://github.com/tporadowski/redis/releases
2. 解压后运行 `redis-server.exe`
3. 或者使用WSL2安装Linux版本

#### macOS
```bash
# 使用Homebrew安装
brew install redis

# 启动Redis服务
brew services start redis

# 测试连接
redis-cli ping
# 应返回 PONG
```

#### Linux
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install redis-server

# 启动Redis服务
sudo systemctl start redis-server
sudo systemctl enable redis-server

# 测试连接
redis-cli ping
```

### 2. 配置Redis（可选）

如需修改Redis配置，编辑 `/etc/redis/redis.conf`：

```conf
# 设置密码
requirepass your_redis_password

# 允许远程连接（谨慎使用）
bind 0.0.0.0

# 持久化配置
save 900 1
save 300 10
save 60 10000
```

### 3. 修改后端配置

如果设置了Redis密码，编辑 `backend/src/main/resources/application.yml`：

```yaml
spring:
  data:
    redis:
      host: localhost
      port: 6379
      password: your_redis_password  # 设置密码
      database: 0
```

---

## 💻 IDE配置

### 后端开发 - IntelliJ IDEA

#### 1. 安装IDEA
- 下载：https://www.jetbrains.com/idea/download/
- 推荐使用Ultimate版本（学生免费）
- Community版本也可以使用

#### 2. 导入项目
1. 打开IDEA
2. File → Open
3. 选择 `task-board-system/backend` 目录
4. 等待Maven依赖下载完成

#### 3. 配置运行
1. 找到 `TaskBoardApplication.java`
2. 右键 → Run 'TaskBoardApplication'
3. 或者点击类旁边的绿色运行按钮

#### 4. 推荐插件
- Lombok Plugin（必装）
- MyBatisX
- Rainbow Brackets
- Translation
- GitToolBox

### 前端开发 - VS Code

#### 1. 安装VS Code
- 下载：https://code.visualstudio.com/

#### 2. 打开项目
1. 打开VS Code
2. File → Open Folder
3. 选择 `task-board-system/frontend` 目录

#### 3. 安装依赖
```bash
# 在VS Code终端中执行
npm install
```

#### 4. 启动开发服务器
```bash
npm run dev
```

#### 5. 推荐插件
- Volar（Vue 3必装）
- ESLint
- Prettier
- Auto Rename Tag
- Path Intellisense
- GitLens
- Chinese (Simplified) Language Pack

---

## 🚀 启动项目

### 1. 启动顺序

```bash
# 1. 确保MySQL和Redis已启动
sudo systemctl status mysql
sudo systemctl status redis

# 2. 启动后端（在backend目录）
cd backend
mvn spring-boot:run

# 3. 启动前端（在frontend目录，新终端）
cd frontend
npm run dev
```

### 2. 访问地址

- 前端：http://localhost:3000
- 后端API：http://localhost:8080/api
- API文档：http://localhost:8080/api/doc.html

### 3. 测试账号

```
用户名：admin
密码：123456
```

---

## ❓ 常见问题

### 问题1：端口被占用

**错误信息：**
```
Web server failed to start. Port 8080 was already in use.
```

**解决方案：**
```bash
# Windows
netstat -ano | findstr :8080
taskkill /PID <进程ID> /F

# macOS/Linux
lsof -i :8080
kill -9 <进程ID>

# 或者修改配置文件中的端口号
```

### 问题2：MySQL连接失败

**错误信息：**
```
Unable to connect to MySQL server
```

**解决方案：**
1. 检查MySQL服务是否启动
2. 检查用户名密码是否正确
3. 检查数据库是否已创建
4. 检查防火墙设置

### 问题3：Maven依赖下载失败

**解决方案：**
1. 配置国内镜像（见上文）
2. 删除 `~/.m2/repository` 重新下载
3. 检查网络连接
4. 使用IDE的Maven重新导入

### 问题4：npm install失败

**解决方案：**
```bash
# 清除缓存
npm cache clean --force

# 删除node_modules和package-lock.json
rm -rf node_modules package-lock.json

# 使用国内镜像重新安装
npm install --registry=https://registry.npmmirror.com
```

### 问题5：Redis连接失败

**解决方案：**
1. 检查Redis服务是否启动
   ```bash
   redis-cli ping
   ```
2. 检查Redis密码配置
3. 检查端口是否正确（默认6379）

---

## 📞 获取帮助

如果遇到其他问题：
1. 查看项目README.md
2. 查看各服务的日志文件
3. 搜索错误信息
4. 提交Issue

---

## 🎉 配置完成

如果所有步骤都成功完成，恭喜你！开发环境已经配置好了。

现在可以开始开发了：
1. 启动后端和前端服务
2. 访问 http://localhost:3000
3. 使用测试账号登录
4. 开始你的开发之旅！

---

<div align="center">

**祝开发顺利！����**

有问题随时查阅此文档或联系项目维护者

</div>
