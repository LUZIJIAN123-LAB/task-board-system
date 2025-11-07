package com.taskboard.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 任务标签关联实体类
 * 对应数据库表：tb_task_tag
 *
 * @author 哈雷酱
 * @date 2025
 */
@Data
@Entity
@Table(name = "tb_task_tag",
    uniqueConstraints = @UniqueConstraint(name = "uk_task_tag", columnNames = {"task_id", "tag_id"}),
    indexes = @Index(name = "idx_tag_id", columnList = "tag_id")
)
public class TaskTag {

    /**
     * ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 任务ID
     */
    @Column(name = "task_id", nullable = false)
    private Long taskId;

    /**
     * 标签ID
     */
    @Column(name = "tag_id", nullable = false)
    private Long tagId;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;
}
