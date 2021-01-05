package com.qiuhuu.test;

import com.qiuhuu.test.dao.jpa.SysUserDao;
import com.qiuhuu.test.dao.jpa.UserDao;
import com.qiuhuu.test.entity.mybatis.SysUser;
import com.qiuhuu.test.entity.mybatis.User;
import com.qiuhuu.test.service.SysUserService;
import com.qiuhuu.test.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
        SysUser sysUser = sysUserService.selectByPrimaryKey(1);
        System.out.println("mybatis 1:" + sysUser.toString());
        User user = userService.selectByPrimaryKey(1);
        System.out.println("mybatis 2:" + user.toString());

        com.qiuhuu.test.entity.jpa.SysUser byId1 = sysUserDao.findById(1);
        System.out.println("jpa 1:" + byId1.toString());
        com.qiuhuu.test.entity.jpa.User byId = userDao.findById(1);
        System.out.println("jpa 2:" + byId.toString());

    }

}
