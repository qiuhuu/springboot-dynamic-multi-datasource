package com.qiuhuu.test.dao.jpa.primary;

import com.qiuhuu.test.entity.jpa.primary.SysUser;
import com.qiuhuu.test.type.DataBaseType;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author : qiuhuu
 * @date : 2020-11-11 14:27
 */
public interface SysUserDao extends JpaRepository<SysUser,Integer> {


    @com.qiuhuu.test.annotation.DataSource(DataBaseType.Master)
    SysUser findById(int id);
}
