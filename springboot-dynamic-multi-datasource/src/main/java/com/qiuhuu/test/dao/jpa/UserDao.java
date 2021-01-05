package com.qiuhuu.test.dao.jpa;

import com.qiuhuu.test.annotation.DataSource;
import com.qiuhuu.test.entity.jpa.User;
import com.qiuhuu.test.type.DataBaseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.websocket.server.PathParam;


/**
 * @author : qiuhuu
 * @date : 2020-11-11 14:30
 */
public interface UserDao extends JpaRepository<User,Integer> {
    @DataSource("datasource")
    User findById(@Param("id") int id);
}
