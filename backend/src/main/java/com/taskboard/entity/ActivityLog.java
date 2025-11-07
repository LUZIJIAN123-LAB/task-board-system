package com.taskboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 操作日志实体类
 * 对应数据库表：tb_activity_log
 *
 * @author 哈雷酱
 * @date 2025
 */
@Data
@Entity
@Table(name = "tb_activity_log", indexes = {
    @Index(name = "idx_board_id", columnList = "board_id"),
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_create_time", columnList = "create_time")
})
public class ActivityLog {

    /**
     * 日志ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 看板ID
     */
    @Column(name = "board_id", nullable = false)
    private Long boardId;

    /**
     * 操作者ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 操作类型：create_task/update_task/delete_task/move_task等
     */
    @NotBlank(message = "操作类型不能为空")
    @Column(name = "action_type", nullable = false, length = 50)
    private String actionType;

    /**
     * 目标类型：task/list/board
     */
    @Column(name = "target_type", length = 50)
    private String targetType;

    /**
     * 目标ID
     */
    @Column(name = "target_id")
    private Long targetId;

    /**
     * 操作描述
     */
    @Column(length = 500)
    private String description;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;
}
