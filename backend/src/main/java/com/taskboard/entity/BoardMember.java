package com.taskboard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 看板成员实体类
 * 对应数据库表：tb_board_member
 *
 * @author 哈雷酱
 * @date 2025
 */
@Data
@Entity
@Table(name = "tb_board_member",
    uniqueConstraints = @UniqueConstraint(name = "uk_board_user", columnNames = {"board_id", "user_id"}),
    indexes = @Index(name = "idx_user_id", columnList = "user_id")
)
public class BoardMember {

    /**
     * ID
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
     * 用户ID
     */
    @Column(name = "user_id", nullable = false)
    private Long userId;

    /**
     * 角色：owner-所有者 editor-编辑者 viewer-查看者
     */
    @NotBlank(message = "角色不能为空")
    @Column(nullable = false, length = 20, columnDefinition = "VARCHAR(20) DEFAULT 'viewer'")
    private String role = "viewer";

    /**
     * 加入时间
     */
    @CreationTimestamp
    @Column(name = "create_time", nullable = false, updatable = false)
    private LocalDateTime createTime;
}
