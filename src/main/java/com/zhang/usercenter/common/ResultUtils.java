package com.zhang.usercenter.common;

/**
 * @program: user-center
 * @description: 返回工具类
 * @version: 1.0
 * @author: Zhanghangfei
 * @create: 2023-04-03 16:24
 **/

public class ResultUtils {

    /**
     * 成功
     *
     * @param data
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(0, data, "ok");
    }

    /**
     * 失败
     *
     * @param errorCode
     * @param <T>
     * @return
     */

    public static <T> BaseResponse<T> error(ErrorCode errorCode) {
        return new BaseResponse<>(errorCode);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @param <T>
     * @return
     */

    public static <T> BaseResponse<T> error(ErrorCode errorCode, String message, String description) {
        return new BaseResponse(errorCode.getCode(), message, description);
    }

    /**
     * 失败
     *
     * @param errorCode
     * @param <T>
     * @return
     */

    public static <T> BaseResponse<T> error(ErrorCode errorCode, String description) {
        return new BaseResponse(errorCode.getCode(),null, errorCode.getMessage(), description);
    }

    /**
     * 失败
     * @param code
     * @param message
     * @param description
     * @param <T>
     * @return
     */
    public static <T> BaseResponse<T> error(int code, String message, String description) {
        return new BaseResponse(code, null,message, description);
    }

}
