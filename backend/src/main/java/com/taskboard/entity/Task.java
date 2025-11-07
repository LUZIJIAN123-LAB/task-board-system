package com.taskboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 任务实体类
 * 对应数据库表：tb_task
 *
 * @author 哈雷酱
 * @date 2025
 */
@Data
@Entity
@Table(name = "tb_task", indexes = {
    @Index(name = "idx_list_id", columnList = "list_id"),
    @Index(name = "idx_board_id", columnList = "board_id"),
    @Index(name = "idx_assignee_id", columnList = "assignee_id"),
    @Index(name = "idx_position", columnList = "position")
})
public class Task {

    /**
     * 任务ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属列表ID
     */
    @Column(name = "list_id", nullable = false)
    private Long listId;

    /**
     * 所属看板ID
     */
    @Column(name = "board_id", nullable = false)
    private Long boardId;

    /**
     * 任务标题
     */
    @NotBlank(message = "任务标题不能为空")
    @Size(max = 200, message = "任务标题长度不能超过200")
    @Column(nullable = false, length = 200)
    private String title;

    /**
     * 任务描述
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * 负责人ID
     */
    @Column(name = "assignee_id")
    private Long assigneeId;

    /**
     * 优先级：0-低 1-中 2-高 3-紧急
     */
    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 1")
    private Integer priority = 1;

    /**
     * 状态：todo-待办 doing-进行中 done-已完成
     */
    @Column(length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'todo'")
    private String status = "todo";

    /**
     * 截止日期
     */
    @Column(name = "due_date")
    private LocalDateTime dueDate;

    /**
     * 位置排序
     */
    @Column(columnDefinition = "INT DEFAULT 0")
    private Integer position = 0;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @UpdateTimestamp
    @Column(name = "update_time", nullable = false)
    private LocalDateTime updateTime;

    /**
     * 逻辑删除：0-未删除 1-已删除
     */
    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private Integer deleted = 0;
}
