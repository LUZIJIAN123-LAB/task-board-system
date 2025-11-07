package com.taskboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

/**
 * 看板实体类
 * 对应数据库表：tb_board
 *
 * @author 哈雷酱
 * @date 2025
 */
@Data
@Entity
@Table(name = "tb_board", indexes = {
    @Index(name = "idx_owner_id", columnList = "owner_id"),
    @Index(name = "idx_create_time", columnList = "create_time")
})
public class Board {

    /**
     * 看板ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 看板标题
     */
    @NotBlank(message = "看板标题不能为空")
    @Size(max = 100, message = "看板标题长度不能超过100")
    @Column(nullable = false, length = 100)
    private String title;

    /**
     * 看板描述
     */
    @Column(columnDefinition = "TEXT")
    private String description;

    /**
     * 所有者ID
     */
    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    /**
     * 背景颜色或图片
     */
    @Column(length = 50, columnDefinition = "VARCHAR(50) DEFAULT '#409EFF'")
    private String background = "#409EFF";

    /**
     * 可见性：0-私有 1-公开
     */
    @Column(nullable = false, columnDefinition = "TINYINT DEFAULT 0")
    private Integer visibility = 0;

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
