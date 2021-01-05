package com.qiuhuu.test.service;

import com.qiuhuu.test.entity.mybatis.SysUser;
    /**
 * @author : qiuhuu
 * @date : 2020-11-11 10:27
 */
public interface SysUserService{


    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

}
