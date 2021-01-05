package com.qiuhuu.test.dao.mybatis;

import com.qiuhuu.test.annotation.DataSource;
import com.qiuhuu.test.entity.mybatis.User;
import com.qiuhuu.test.type.DataBaseType;

/**
 * @author : qiuhuu
 * @date : 2020-11-11 10:29
 */
public interface UserMapper {
    /**
     * delete by primary key
     * @param id primaryKey
     * @return deleteCount
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * insert record to table
     * @param record the record
     * @return insert count
     */
    int insert(User record);

    /**
     * insert record to table selective
     * @param record the record
     * @return insert count
     */
    int insertSelective(User record);

    /**
     * select by primary key
     * @param id primary key
     * @return object by primary key
     */
    @DataSource("Slave")
    User selectByPrimaryKey(Integer id);

    /**
     * update record selective
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * update record
     * @param record the updated record
     * @return update count
     */
    int updateByPrimaryKey(User record);
}