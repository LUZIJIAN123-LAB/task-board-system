package com.taskboard.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一响应结果类
 *
 * @author 哈雷酱
 * @param <T> 数据类型
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 响应消息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 时间戳
     */
    private Long timestamp;

    // ========== 成功响应 ==========

    /**
     * 成功响应（无数据）
     */
    public static <T> Result<T> success() {
        return success(null);
    }

    /**
     * 成功响应（带数据）
     */
    public static <T> Result<T> success(T data) {
        return success("操作成功", data);
    }

    /**
     * 成功响应（自定义消息和数据）
     */
    public static <T> Result<T> success(String message, T data) {
        return new Result<>(200, message, data, System.currentTimeMillis());
    }

    // ========== 失败响应 ==========

    /**
     * 失败响应（默认消息）
     */
    public static <T> Result<T> error() {
        return error("操作失败");
    }

    /**
     * 失败响应（自定义消息）
     */
    public static <T> Result<T> error(String message) {
        return error(500, message);
    }

    /**
     * 失败响应（自定义状态码和消息）
     */
    public static <T> Result<T> error(Integer code, String message) {
        return new Result<>(code, message, null, System.currentTimeMillis());
    }

    // ========== 特殊响应 ==========

    /**
     * 未授权响应
     */
    public static <T> Result<T> unauthorized() {
        return error(401, "未授权，请先登录");
    }

    /**
     * 禁止访问响应
     */
    public static <T> Result<T> forbidden() {
        return error(403, "权限不足，禁止访问");
    }

    /**
     * 资源未找到响应
     */
    public static <T> Result<T> notFound() {
        return error(404, "资源不存在");
    }

}
