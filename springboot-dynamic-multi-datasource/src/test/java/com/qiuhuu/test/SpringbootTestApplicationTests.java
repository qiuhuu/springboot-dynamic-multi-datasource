package com.qiuhuu.test;

import com.qiuhuu.test.dao.jpa.SysUserDao;
import com.qiuhuu.test.dao.jpa.UserDao;
import com.qiuhuu.test.service.SysUserService;
import com.qiuhuu.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class SpringbootTestApplicationTests {

    @Autowired
    SysUserService sysUserService;
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;
    @Autowired
    SysUserDao sysUserDao;

    @Test
    void contextLoads() {
        //List<String> username = sysUserDao.findUsername("admin");
        //List<String> username = sysUserDao.findUsername1(true);
        List<String> username = sysUserDao.findUsername(null);
        //List<String> username = sysUserDao.findUsername2();
        System.out.println(username);
    }

}
