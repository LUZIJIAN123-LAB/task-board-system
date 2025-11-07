package com.taskboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 任务列表实体类
 * 对应数据库表：tb_task_list
 *
 * @author 哈雷酱
 * @date 2025
 */
@Data
@Entity
@Table(name = "tb_task_list", indexes = {
    @Index(name = "idx_board_id", columnList = "board_id"),
    @Index(name = "idx_position", columnList = "position")
})
public class TaskList {

    /**
     * 列表ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属看板ID
     */
    @Column(name = "board_id", nullable = false)
    private Long boardId;

    /**
     * 列表标题
     */
    @NotBlank(message = "列表标题不能为空")
    @Size(max = 100, message = "列表标题长度不能超过100")
    @Column(nullable = false, length = 100)
    private String title;

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
