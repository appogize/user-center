package com.zhang.usercenter.service;

import com.zhang.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
 *用户服务
 */
public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 校验密码
     * @param planetCode 星球编号
     * @return
     */
    long userRegister(String userAccount ,String userPassword,String checkPassword,String planetCode);

    /**
     * 用户登录
     * @param userAccount 账号
     * @param userPassword 密码
     * @return
     */
    User userLogin(String userAccount , String userPassword, HttpServletRequest request);


    /**
     * 用户注销
     */
    int userLogout(HttpServletRequest request);

    /**
     * 用户信息脱敏
     * @param originUser
     * @return
     */
    User getSafetyUser(User originUser);
}
