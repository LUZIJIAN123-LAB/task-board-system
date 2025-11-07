# 数据库表结构设计文档

## 📊 ER图概述

```
┌─────────────┐
│   tb_user   │ 用户表
└──────┬──────┘
       │
       │ 1:N
       ▼
┌─────────────────┐
│    tb_board     │ 看板表
└────────┬────────┘
         │
    ┌────┴────┐
    │         │
    │ 1:N     │ 1:N
    ▼         ▼
┌──────────┐  ┌────────────────┐
│tb_board_ │  │ tb_task_list   │ 任务列表表
│ member   │  └────────┬───────┘
└──────────┘           │
                       │ 1:N
                       ▼
                  ┌──────────┐
                  │ tb_task  │ 任务表
                  └─────┬────┘
                        │
             ┌──────────┼──────────┐
             │          │          │
             │ N:M      │ 1:N      │ 1:N
             ▼          ▼          ▼
        ┌─────────┐ ┌──────────┐ ┌──────────┐
        │ tb_tag  │ │tb_comment│ │tb_activity│
        │         │ │          │ │   _log   │
        └─────────┘ └──────────┘ └──────────┘
             │
             │ N:M
             ▼
        ┌──────────┐
        │tb_task_  │
        │   tag    │
        └──────────┘
```

## 📋 核心表详细说明

### 1. tb_user (用户表)

**功能：** 存储系统用户信息

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 用户ID | 主键、自增 |
| username | VARCHAR(50) | 用户名 | 唯一、非空 |
| password | VARCHAR(255) | 密码（BCrypt加密） | 非空 |
| nickname | VARCHAR(50) | 昵称 | 可空 |
| email | VARCHAR(100) | 邮箱 | 唯一 |
| avatar | VARCHAR(500) | 头像URL | 可空 |
| status | TINYINT | 状态：0-禁用 1-启用 | 默认1 |
| create_time | DATETIME | 创建时间 | 默认当前时间 |
| update_time | DATETIME | 更新时间 | 自动更新 |
| deleted | TINYINT | 逻辑删除标记 | 默认0 |

**索引：**
- PRIMARY KEY: `id`
- UNIQUE KEY: `uk_username(username)`
- UNIQUE KEY: `uk_email(email)`
- KEY: `idx_create_time(create_time)`

---

### 2. tb_board (看板表)

**功能：** 存储看板信息

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 看板ID | 主键、自增 |
| title | VARCHAR(100) | 看板标题 | 非空 |
| description | TEXT | 看板描述 | 可空 |
| owner_id | BIGINT | 所有者ID | 非空、外键 |
| background | VARCHAR(50) | 背景颜色/图片 | 默认#409EFF |
| visibility | TINYINT | 可见性：0-私有 1-公开 | 默认0 |
| create_time | DATETIME | 创建时间 | 默认当前时间 |
| update_time | DATETIME | 更新时间 | 自动更新 |
| deleted | TINYINT | 逻辑删除标记 | 默认0 |

**索引：**
- PRIMARY KEY: `id`
- KEY: `idx_owner_id(owner_id)`
- KEY: `idx_create_time(create_time)`

---

### 3. tb_board_member (看板成员表)

**功能：** 管理看板成员和权限

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | ID | 主键、自增 |
| board_id | BIGINT | 看板ID | 非空、外键 |
| user_id | BIGINT | 用户ID | 非空、外键 |
| role | VARCHAR(20) | 角色：owner/editor/viewer | 非空、默认viewer |
| create_time | DATETIME | 加入时间 | 默认当前时间 |

**索引：**
- PRIMARY KEY: `id`
- UNIQUE KEY: `uk_board_user(board_id, user_id)`
- KEY: `idx_user_id(user_id)`

**权限说明：**
- `owner`: 所有者，拥有全部权限
- `editor`: 编辑者，可编辑看板和任务
- `viewer`: 查看者，仅可查看

---

### 4. tb_task_list (任务列表表)

**功能：** 看板中的任务列（如：待办、进行中、已完成）

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 列表ID | 主键、自增 |
| board_id | BIGINT | 所属看板ID | 非空、外键 |
| title | VARCHAR(100) | 列表标题 | 非空 |
| position | INT | 位置排序 | 默认0 |
| create_time | DATETIME | 创建时间 | 默认当前时间 |
| update_time | DATETIME | 更新时间 | 自动更新 |
| deleted | TINYINT | 逻辑删除标记 | 默认0 |

**索引：**
- PRIMARY KEY: `id`
- KEY: `idx_board_id(board_id)`
- KEY: `idx_position(position)`

---

### 5. tb_task (任务表)

**功能：** 存储任务详细信息

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 任务ID | 主键、自增 |
| list_id | BIGINT | 所属列表ID | 非空、外键 |
| board_id | BIGINT | 所属看板ID | 非空、外键 |
| title | VARCHAR(200) | 任务标题 | 非空 |
| description | TEXT | 任务描述 | 可空 |
| assignee_id | BIGINT | 负责人ID | 可空、外键 |
| priority | TINYINT | 优先级：0-低 1-中 2-高 3-紧急 | 默认1 |
| status | VARCHAR(20) | 状态：todo/doing/done | 默认todo |
| due_date | DATETIME | 截止日期 | 可空 |
| position | INT | 位置排序 | 默认0 |
| create_time | DATETIME | 创建时间 | 默认当前时间 |
| update_time | DATETIME | 更新时间 | 自动更新 |
| deleted | TINYINT | 逻辑删除标记 | 默认0 |

**索引：**
- PRIMARY KEY: `id`
- KEY: `idx_list_id(list_id)`
- KEY: `idx_board_id(board_id)`
- KEY: `idx_assignee_id(assignee_id)`
- KEY: `idx_position(position)`

---

### 6. tb_tag (标签表)

**功能：** 任务标签管理

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 标签ID | 主键、自增 |
| board_id | BIGINT | 所属看板ID | 非空、外键 |
| name | VARCHAR(50) | 标签名称 | 非空 |
| color | VARCHAR(20) | 标签颜色 | 默认#409EFF |
| create_time | DATETIME | 创建时间 | 默认当前时间 |
| deleted | TINYINT | 逻辑删除标记 | 默认0 |

---

### 7. tb_task_tag (任务标签关联表)

**功能：** 任务与标签的多对多关系

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | ID | 主键、自增 |
| task_id | BIGINT | 任务ID | 非空、外键 |
| tag_id | BIGINT | 标签ID | 非空、外键 |
| create_time | DATETIME | 创建时间 | 默认当前时间 |

**索引：**
- UNIQUE KEY: `uk_task_tag(task_id, tag_id)`

---

### 8. tb_comment (评论表)

**功能：** 任务评论

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 评论ID | 主键、自增 |
| task_id | BIGINT | 任务ID | 非空、外键 |
| user_id | BIGINT | 评论者ID | 非空、外键 |
| content | TEXT | 评论内容 | 非空 |
| create_time | DATETIME | 创建时间 | 默认当前时间 |
| update_time | DATETIME | 更新时间 | 自动更新 |
| deleted | TINYINT | 逻辑删除标记 | 默认0 |

---

### 9. tb_activity_log (操作日志表)

**功能：** 记录看板操作历史

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 日志ID | 主键、自增 |
| board_id | BIGINT | 看板ID | 非空、外键 |
| user_id | BIGINT | 操作者ID | 非空、外键 |
| action_type | VARCHAR(50) | 操作类型 | 非空 |
| target_type | VARCHAR(50) | 目标类型：task/list/board | 可空 |
| target_id | BIGINT | 目标ID | 可空 |
| description | VARCHAR(500) | 操作描述 | 可空 |
| create_time | DATETIME | 创建时间 | 默认当前时间 |

**操作类型示例：**
- `create_task`: 创建任务
- `update_task`: 更新任务
- `delete_task`: 删除任务
- `move_task`: 移动任务
- `assign_task`: 分配任务
- `add_member`: 添加成员

---

### 10. tb_notification (通知表)

**功能：** 用户消息通知

| 字段名 | 类型 | 说明 | 约束 |
|--------|------|------|------|
| id | BIGINT | 通知ID | 主键、自增 |
| user_id | BIGINT | 接收者ID | 非空、外键 |
| type | VARCHAR(50) | 通知类型 | 非空 |
| title | VARCHAR(200) | 通知标题 | 非空 |
| content | TEXT | 通知内容 | 可空 |
| related_id | BIGINT | 关联对象ID | 可空 |
| is_read | TINYINT | 是否已读：0-未读 1-已读 | 默认0 |
| create_time | DATETIME | 创建时间 | 默认当前时间 |

**通知类型示例：**
- `task_assigned`: 任务被分配
- `task_updated`: 任务被更新
- `comment_added`: 新增评论
- `due_date_reminder`: 截止日期提醒

---

## 🔑 表关系说明

### 一对多关系 (1:N)
- 用户 → 看板 (一个用户可创建多个看板)
- 看板 → 任务列表 (一个看板包含多个列表)
- 任务列表 → 任务 (一个列表包含多个任务)
- 任务 → 评论 (一个任务可有多条评论)

### 多对多关系 (N:M)
- 看板 ↔ 用户 (通过 `tb_board_member`)
- 任务 ↔ 标签 (通过 `tb_task_tag`)

---

## 📈 设计亮点

1. **逻辑删除**：所有主表都使用 `deleted` 字段实��软删除，保留数据历史
2. **乐观锁**：使用 `update_time` 字段支持并发控制
3. **权限分级**：通过 `role` 字段实现 RBAC 权限模型
4. **排序支持**：`position` 字段支持拖拽排序
5. **时间追踪**：创建和更新时间自动维护
6. **索引优化**：为高频查询字段建立索引

---

## 🚀 初始化说明

1. 执行 `init.sql` 创建数据库和表
2. 默认插入3个测试用户（密码均为：123456）
3. 创建2个测试看板和若干���试任务
4. 可直接用于开发和测试
