package com.zhang.usercenter.service;

import com.zhang.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;


/**
 * 用户服务测试
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    void addTest() {
        User user = new User();
        user.setUsername("zhang");
        user.setUserAccount("123");
        user.setAvatarUrl("https://cn.bing.com/images/search?view=detailV2&ccid=4TuozHK3&id=AA9A1CB7CDE3316BA6337DF859440913BB755237&thid=OIP.4TuozHK3xWwyalBaqaymiAHaHa&mediaurl=https%3a%2f%2fts1.cn.mm.bing.net%2fth%2fid%2fR-C.e13ba8cc72b7c56c326a505aa9aca688%3frik%3dN1J1uxMJRFn4fQ%26riu%3dhttp%253a%252f%252fimg3.100bt.com%252fupload%252fttq%252f20130131%252f1359604487139.jpg%26ehk%3dWINUl3WXTizVTVlZx7UGhIopVN2NUHdgOiQRoSb%252fQTY%253d%26risl%3d%26pid%3dImgRaw%26r%3d0&exph=800&expw=800&q=%e5%b0%8f%e9%bb%91%e5%ad%90&simid=608011634092949652&FORM=IRPRST&ck=C8754D7B345B383CEC8ED7E6B05E7372&selectedIndex=0&ajaxhist=0&ajaxserp=0");
        user.setGender((byte) 0);
        user.setUserPassword("12345678");
        user.setPhone("8888888");
        user.setEmail("3225879907@qq.com");
        boolean res = userService.save(user);
        System.out.println(user.getId());
        Assertions.assertTrue(res);//断言
    }

    @Test
    void userRegister() {
        String userAccount = "zhangSan";
        String userPassword = "";
        String checkPassword = "12345678";
        String planetCode = "18";
        long res = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, res);

        userAccount = "zf";
        res = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, res);

        userAccount = "zhangSan";
        userPassword = "123456";
        res = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, res);

        userAccount = "z  %,?e";
        userPassword = "12345678";
        res = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, res);


        checkPassword = "123456789";
        res = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, res);

        userAccount = "123";
        checkPassword = "12345678";
        res = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        Assertions.assertEquals(-1, res);

        userAccount = "aSan";
        res = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        //Assertions.assertTrue(res > 0);
        Assertions.assertEquals(-1, res);

    }
}