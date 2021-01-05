package com.qiuhuu.test.dao.jpa.secondary;

import com.qiuhuu.test.entity.jpa.secondary.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * @author : qiuhuu
 * @date : 2020-11-11 14:30
 */
public interface UserDao extends JpaRepository<User,Integer> {
    User findById(int id);
}
