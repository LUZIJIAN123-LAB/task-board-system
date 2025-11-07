-- ====================================
-- 任务看板系统数据库初始化脚本
-- 作者：哈雷酱
-- 日期：2025
-- ====================================

-- 创建数据库
CREATE DATABASE IF NOT EXISTS `task_board` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE `task_board`;

-- ====================================
-- 用户表
-- ====================================
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '用户ID',
    `username` VARCHAR(50) NOT NULL COMMENT '用户名',
    `password` VARCHAR(255) NOT NULL COMMENT '密码（加密）',
    `nickname` VARCHAR(50) DEFAULT NULL COMMENT '昵称',
    `email` VARCHAR(100) DEFAULT NULL COMMENT '邮箱',
    `avatar` VARCHAR(500) DEFAULT NULL COMMENT '头像URL',
    `status` TINYINT DEFAULT 1 COMMENT '状态：0-禁用 1-启用',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_username` (`username`),
    UNIQUE KEY `uk_email` (`email`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- ====================================
-- 看板表
-- ====================================
DROP TABLE IF EXISTS `tb_board`;
CREATE TABLE `tb_board` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '看板ID',
    `title` VARCHAR(100) NOT NULL COMMENT '看板标题',
    `description` TEXT COMMENT '看板描述',
    `owner_id` BIGINT NOT NULL COMMENT '所有者ID',
    `background` VARCHAR(50) DEFAULT '#409EFF' COMMENT '背景颜色或图片',
    `visibility` TINYINT DEFAULT 0 COMMENT '可见性：0-私有 1-公开',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_owner_id` (`owner_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='看板表';

-- ====================================
-- 看板成员表
-- ====================================
DROP TABLE IF EXISTS `tb_board_member`;
CREATE TABLE `tb_board_member` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `board_id` BIGINT NOT NULL COMMENT '看板ID',
    `user_id` BIGINT NOT NULL COMMENT '用户ID',
    `role` VARCHAR(20) NOT NULL DEFAULT 'viewer' COMMENT '角色：owner-所有者 editor-编辑者 viewer-查看者',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_board_user` (`board_id`, `user_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='看板成员表';

-- ====================================
-- 任务列表表
-- ====================================
DROP TABLE IF EXISTS `tb_task_list`;
CREATE TABLE `tb_task_list` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '列表ID',
    `board_id` BIGINT NOT NULL COMMENT '所属看板ID',
    `title` VARCHAR(100) NOT NULL COMMENT '列表标题',
    `position` INT DEFAULT 0 COMMENT '位置排序',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_board_id` (`board_id`),
    KEY `idx_position` (`position`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务列表表';

-- ====================================
-- 任务表
-- ====================================
DROP TABLE IF EXISTS `tb_task`;
CREATE TABLE `tb_task` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '任务ID',
    `list_id` BIGINT NOT NULL COMMENT '所属列表ID',
    `board_id` BIGINT NOT NULL COMMENT '所属看板ID',
    `title` VARCHAR(200) NOT NULL COMMENT '任务标题',
    `description` TEXT COMMENT '任务描述',
    `assignee_id` BIGINT DEFAULT NULL COMMENT '负责人ID',
    `priority` TINYINT DEFAULT 1 COMMENT '优先级：0-低 1-中 2-高 3-紧急',
    `status` VARCHAR(20) DEFAULT 'todo' COMMENT '状态：todo-待办 doing-进行中 done-已完成',
    `due_date` DATETIME DEFAULT NULL COMMENT '截止日期',
    `position` INT DEFAULT 0 COMMENT '位置排序',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_list_id` (`list_id`),
    KEY `idx_board_id` (`board_id`),
    KEY `idx_assignee_id` (`assignee_id`),
    KEY `idx_position` (`position`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务表';

-- ====================================
-- 标签表
-- ====================================
DROP TABLE IF EXISTS `tb_tag`;
CREATE TABLE `tb_tag` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '标签ID',
    `board_id` BIGINT NOT NULL COMMENT '所属看板ID',
    `name` VARCHAR(50) NOT NULL COMMENT '标签名称',
    `color` VARCHAR(20) DEFAULT '#409EFF' COMMENT '标签颜色',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_board_id` (`board_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='标签表';

-- ====================================
-- 任务标��关联表
-- ====================================
DROP TABLE IF EXISTS `tb_task_tag`;
CREATE TABLE `tb_task_tag` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT 'ID',
    `task_id` BIGINT NOT NULL COMMENT '任务ID',
    `tag_id` BIGINT NOT NULL COMMENT '标签ID',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_task_tag` (`task_id`, `tag_id`),
    KEY `idx_tag_id` (`tag_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='任务标签关联表';

-- ====================================
-- 评论表
-- ====================================
DROP TABLE IF EXISTS `tb_comment`;
CREATE TABLE `tb_comment` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '评论ID',
    `task_id` BIGINT NOT NULL COMMENT '任务ID',
    `user_id` BIGINT NOT NULL COMMENT '评论者ID',
    `content` TEXT NOT NULL COMMENT '评论内容',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` TINYINT DEFAULT 0 COMMENT '逻辑删除：0-未删除 1-已删除',
    PRIMARY KEY (`id`),
    KEY `idx_task_id` (`task_id`),
    KEY `idx_user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='评论表';

-- ====================================
-- 操作日志表
-- ====================================
DROP TABLE IF EXISTS `tb_activity_log`;
CREATE TABLE `tb_activity_log` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '日志ID',
    `board_id` BIGINT NOT NULL COMMENT '看板ID',
    `user_id` BIGINT NOT NULL COMMENT '操作者ID',
    `action_type` VARCHAR(50) NOT NULL COMMENT '操作类型：create_task/update_task/delete_task/move_task等',
    `target_type` VARCHAR(50) DEFAULT NULL COMMENT '目标类型：task/list/board',
    `target_id` BIGINT DEFAULT NULL COMMENT '目标ID',
    `description` VARCHAR(500) DEFAULT NULL COMMENT '操作描述',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_board_id` (`board_id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='操作日志表';

-- ====================================
-- 通知表
-- ====================================
DROP TABLE IF EXISTS `tb_notification`;
CREATE TABLE `tb_notification` (
    `id` BIGINT NOT NULL AUTO_INCREMENT COMMENT '通知ID',
    `user_id` BIGINT NOT NULL COMMENT '接收者ID',
    `type` VARCHAR(50) NOT NULL COMMENT '通知类型：task_assigned/task_updated/comment_added等',
    `title` VARCHAR(200) NOT NULL COMMENT '通知标题',
    `content` TEXT COMMENT '通知内容',
    `related_id` BIGINT DEFAULT NULL COMMENT '关联对象ID',
    `is_read` TINYINT DEFAULT 0 COMMENT '是否已读：0-未读 1-已读',
    `create_time` DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    PRIMARY KEY (`id`),
    KEY `idx_user_id` (`user_id`),
    KEY `idx_is_read` (`is_read`),
    KEY `idx_create_time` (`create_time`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='通知表';

-- ====================================
-- 插入测试数据
-- ====================================

-- 插入测试用户（密码都是：123456，BCrypt加密后的值）
INSERT INTO `tb_user` (`username`, `password`, `nickname`, `email`, `avatar`) VALUES
('admin', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '管理员', 'admin@taskboard.com', NULL),
('user1', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '用户1', 'user1@taskboard.com', NULL),
('user2', '$2a$10$N.zmdr9k7uOCQb376NoUnuTJ8iAt6Z5EHsM8lE9lBOsl7iKTVKIUi', '用户2', 'user2@taskboard.com', NULL);

-- 插入测试看板
INSERT INTO `tb_board` (`title`, `description`, `owner_id`, `background`) VALUES
('我的第一个看板', '这是一个测试看板', 1, '#409EFF'),
('项目开发看板', '用于项目开发任务管理', 1, '#67C23A');

-- 插入看板成员
INSERT INTO `tb_board_member` (`board_id`, `user_id`, `role`) VALUES
(1, 1, 'owner'),
(1, 2, 'editor'),
(2, 1, 'owner');

-- 插入任务列表
INSERT INTO `tb_task_list` (`board_id`, `title`, `position`) VALUES
(1, '待办', 0),
(1, '进行中', 1),
(1, '已完成', 2);

-- 插入测试任务
INSERT INTO `tb_task` (`list_id`, `board_id`, `title`, `description`, `priority`, `status`, `position`) VALUES
(1, 1, '学习Spring Boot', '掌握Spring Boot基础知识', 2, 'todo', 0),
(1, 1, '学习Vue 3', '学习Vue 3 Composition API', 2, 'todo', 1),
(2, 1, '搭建项目框架', '完成前后端项目初始化', 3, 'doing', 0);

-- 插入标签
INSERT INTO `tb_tag` (`board_id`, `name`, `color`) VALUES
(1, '前端', '#409EFF'),
(1, '后端', '#67C23A'),
(1, '紧急', '#F56C6C');

-- ====================================
-- 完成！
-- ====================================
