package com.zhang.usercenter.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @program: user-center
 * @description: 用户注册请求体
 * @version: 1.0
 * @author: Zhanghangfei
 * @create: 2023-03-30 00:58
 **/
@Data
public class UserRegisterRequest implements Serializable {


    private String userAccount;

    private String userPassword;

    private String checkPassword;

    private String planetCode;

    private static final long serialVersionUID = 6012117179958969900L;
}
