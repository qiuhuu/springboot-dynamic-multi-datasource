package com.qiuhuu.test.controller;

import com.qiuhuu.test.dao.jpa.SysUserDao;
import com.qiuhuu.test.dao.jpa.UserDao;
import com.qiuhuu.test.entity.mybatis.SysUser;
import com.qiuhuu.test.entity.mybatis.User;
import com.qiuhuu.test.service.SysUserService;
import com.qiuhuu.test.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : qiuhuu
 * @date : 2020-11-09 16:17
 */
@RestController
public class TestController {
    @Autowired
    SysUserService sysUserService;
    @Autowired
    UserService userService;

    @Autowired
    UserDao userDao;
    @Autowired
    SysUserDao sysUserDao;

    @GetMapping("{option}")
    public String test(@PathVariable String option){
        switch (option){
            case "1":
                SysUser sysUser = sysUserService.selectByPrimaryKey(1);
                return sysUser.toString();
            case "2":
                User user = userService.selectByPrimaryKey(1);
                return user.toString();
            case "3":
                com.qiuhuu.test.entity.jpa.SysUser byId1 = sysUserDao.findById(1);
                return byId1.toString();
            default:
                com.qiuhuu.test.entity.jpa.User byId = userDao.findById(1);
                return byId.toString();
        }
    }
}
