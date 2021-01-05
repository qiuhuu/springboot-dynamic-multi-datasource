package com.qiuhuu.test.dao.jpa;

import com.qiuhuu.test.entity.jpa.SysUser;
import com.qiuhuu.test.type.DataBaseType;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author : qiuhuu
 * @date : 2020-11-11 14:27
 */
public interface SysUserDao extends JpaRepository<SysUser,Integer> {

    SysUser findById(int id);
}
