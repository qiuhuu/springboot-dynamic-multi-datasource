package com.qiuhuu.test.service.impl;

import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.qiuhuu.test.entity.mybatis.primary.SysUser;
import com.qiuhuu.test.dao.mybatis.master.SysUserMapper;
import com.qiuhuu.test.service.SysUserService;
/**
 * @author : qiuhuu
 * @date : 2020-11-11 10:27
 */
@Service
public class SysUserServiceImpl implements SysUserService{

    @Resource
    private SysUserMapper sysUserMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return sysUserMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(SysUser record) {
        return sysUserMapper.insert(record);
    }

    @Override
    public int insertSelective(SysUser record) {
        return sysUserMapper.insertSelective(record);
    }

    @Override
    public SysUser selectByPrimaryKey(Integer id) {
        return sysUserMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(SysUser record) {
        return sysUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(SysUser record) {
        return sysUserMapper.updateByPrimaryKey(record);
    }

}
