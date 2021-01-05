package com.qiuhuu.test.dao.jpa;

import com.qiuhuu.test.annotation.DataSource;
import com.qiuhuu.test.entity.jpa.SysUser;
import com.qiuhuu.test.type.DataBaseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


/**
 * @author : qiuhuu
 * @date : 2020-11-11 14:27
 */
public interface SysUserDao extends JpaRepository<SysUser,Integer> {

    SysUser findById(int id);

    @Override
    Optional<SysUser> findById(Integer integer);

    @Query(nativeQuery = true,value = "select username from sys_user where username = IF(:flag, 'admin','qiuhuu' )")
    List<String> findUsername1(@Param("flag") boolean flag);

    @DataSource("node")
    //@Query(nativeQuery = true,
    //        value =
    //                "SELECT NIKNAME from DK_USER_ACCOUNT where if(?1!= '',NIKNAME =?2,PHONE = ?3) ")

    @Query(nativeQuery = true,
            value =
                    "SELECT NIKNAME from DK_USER_ACCOUNT where NIKNAME = DECODE(NVL(:flag,''),'', 'admin','qiuhuu' ) ")
    List<String> findUsername(@Param("flag") String flag);

    @DataSource("node")
    @Query(nativeQuery = true,value = "select \"username\" from \"sys_user\" ")
    List<String> findUsername2();

}
