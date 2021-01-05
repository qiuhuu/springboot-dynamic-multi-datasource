package com.qiuhuu.test.service;

import com.qiuhuu.test.entity.mybatis.slave.User;
    /**
 * @author : qiuhuu
 * @date : 2020-11-11 10:29
 */
public interface UserService{


    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}
