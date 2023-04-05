package com.zhang.usercenter.exception;

import com.zhang.usercenter.common.ErrorCode;

/**
 * @program: user-center
 * @description: 自定义异常类
 * @version: 1.0
 * @author: Zhanghangfei
 * @create: 2023-04-03 17:17
 **/

public class BusinessException extends RuntimeException {

    private final int code;

    private final String description;

    public BusinessException(String message, int code, String description) {
        super(message);
        this.code = code;
        this.description = description;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = errorCode.getDescription();
    }

    public BusinessException(ErrorCode errorCode, String description) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
