package com.taskboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 通知实体类
 * 对应数据库表：tb_notification
 *
 * @author 哈雷酱
 * @date 2025
 */
@Data
@Entity
@Table(name = "tb_notification", indexes = {
    @Index(name = "idx_user_id", columnList = "user_id"),
    @Index(name = "idx_is_read", columnList = "is_read"),
    @Index(name = "idx_create_time", columnList = "create_time")
})
public class Notification {

    /**
     * 通知ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 接收者ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 通知类型：task_assigned/task_updated/comment_added等
     */
    @NotBlank(message = "通知类型不能为空")
    @Column(nullable = false, length = 50)
    private String type;

    /**
     * 通知标题
     */
    @NotBlank(message = "通知标题不能为空")
    @Size(max = 200, message = "通知标题长度不能超过200")
    @Column(nullable = false, length = 200)
    private String title;

    /**
     * 通知内容
     */
    @Column(columnDefinition = "TEXT")
    private String content;

    /**
     * 关联对象ID
     */
    @Column(name = "related_id")
    private Long relatedId;

    /**
     * 是否已读：0-未读 1-已读
     */
    @Column(name = "is_read", nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private Integer isRead = 0;

    /**
     * 创建时间
     */
    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;
}
