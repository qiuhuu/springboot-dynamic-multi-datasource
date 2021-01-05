package com.qiuhuu.test.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiuhuu.test.entity.mybatis.User;
import com.qiuhuu.test.dao.mybatis.UserMapper;
import com.qiuhuu.test.service.UserService;
/**
 * @author : qiuhuu
 * @date : 2020-11-11 10:29
 */
@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

}
