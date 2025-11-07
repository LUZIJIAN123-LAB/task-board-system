package com.taskboard.exception;

import lombok.Getter;

/**
 * 业务异常类
 *
 * @author 哈雷酱
 */
@Getter
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * 错误码
     */
    private final Integer code;

    /**
     * 错误消息
     */
    private final String message;

    public BusinessException(String message) {
        this(500, message);
    }

    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    /**
     * 创建业务异常
     */
    public static BusinessException of(String message) {
        return new BusinessException(message);
    }

    /**
     * 创建业务异常（带状态码）
     */
    public static BusinessException of(Integer code, String message) {
        return new BusinessException(code, message);
    }

}
