package com.zhang.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhang.usercenter.common.ErrorCode;
import com.zhang.usercenter.exception.BusinessException;
import com.zhang.usercenter.mapper.UserMapper;
import com.zhang.usercenter.model.domain.User;
import com.zhang.usercenter.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.zhang.usercenter.contant.UserConstant.USER_LOGIN_STATE;

/**
 *
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {

    @Resource
    private UserMapper userMapper;
    /**
     * 盐值
     */
    private static final String SALT = "zhang";


    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword, String planetCode) {
        //1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword,planetCode))
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空!");
        if (userAccount.length() < 4)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户账号过短!");
        if (userPassword.length() < 8 || checkPassword.length() < 8)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短!");
        //账号不能出现特殊字符
        if(planetCode.length()>5)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号过长!");
        String validPattern = ".*[\\s`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号包含特殊字符!");
        }
        //密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码和校验密码必须相同!");
        }
        //账号不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号重复!");
        }
        //星球编号不能重复
        queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("planetCode", planetCode);
        count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"星球编号重复!");
        }
        //2.加密
        final String SALT = "zhang";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setPlanetCode(planetCode);
        boolean saveRes = this.save(user);
        if (!saveRes) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"save方法出错!");
        }
        return user.getId();
    }


    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (StringUtils.isAnyBlank(userAccount, userPassword))
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码为空!");
        if (userAccount.length() < 4)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号长度过短!");
        if (userPassword.length() < 8)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"用户密码过短!");
        //账号不能出现特殊字符

        String validPattern = ".*[\\s`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？\\\\]+.*";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号包含特殊字符!");
        }
        //
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        //用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //不存在
        if (user == null) {
            log.info("user login failed,userAccount cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号或密码输入有误!");
        }
        //3.用户信息脱敏
        User safetyUser = getSafetyUser(user);
        //4.记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        //移除登录态
        request.getSession().removeAttribute(USER_LOGIN_STATE);

        throw new BusinessException(ErrorCode.LOGOUT_SUCCESS,"注销登录成功!");
    }

    /**
     * 用户信息脱敏
     */
    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空!");
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserAccount(originUser.getUserAccount());
        safetyUser.setAvatarUrl(originUser.getAvatarUrl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setUserRole(originUser.getUserRole());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setPlanetCode(originUser.getPlanetCode());
        safetyUser.setUserStatus(originUser.getUserStatus());
        safetyUser.setCreateTime(originUser.getCreateTime());
        return safetyUser;
    }
}




