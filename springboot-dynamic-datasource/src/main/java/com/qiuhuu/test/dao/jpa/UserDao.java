package com.qiuhuu.test.dao.jpa;

import com.qiuhuu.test.annotation.DataSource;
import com.qiuhuu.test.entity.jpa.User;
import com.qiuhuu.test.type.DataBaseType;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author : qiuhuu
 * @date : 2020-11-11 14:30
 */
public interface UserDao extends JpaRepository<User,Integer> {
    @DataSource(DataBaseType.Slave)
    User findById(int id);
}
