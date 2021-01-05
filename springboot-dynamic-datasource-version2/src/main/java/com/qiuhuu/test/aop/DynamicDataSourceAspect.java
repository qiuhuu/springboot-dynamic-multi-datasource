package com.qiuhuu.test.aop;

import com.qiuhuu.test.annotation.DataSource;
import com.qiuhuu.test.type.DataBaseType;
import com.qiuhuu.test.type.DataSourceType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author : qiuhuu
 * @date : 2020-11-11 15:48
 */
@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect {


    @Pointcut("args(dataSource) && within(com.qiuhuu.test.dao.mybatis.UserMapper)")
    public void expression(DataSource dataSource) {
    }

    /**
     * 在注解所在的方法执行之前，拦截方法
     * @param point
     * @param dataSource
     */
    //@Before(value = "@within(com.qiuhuu.test.annotation.DataSource) && @args(dataSource)")
    @Before(value = "@annotation(dataSource)")
    public void changeDataSource(JoinPoint point, DataSource dataSource) {
        /*DataBaseType value = dataSource.value();
        log.info("switch to ：{}",value.toString());
        switch (dataSource.value()){
            case Slave:
                DataSourceType.setDataBaseType(DataBaseType.Slave);
                break;
            default:
                DataSourceType.setDataBaseType(DataBaseType.Master);
                break;
        }*/
        String value = dataSource.value();
        log.info("switch to ：{}", value);
        if ("master".equals(value)){
            DataSourceType.setDataBaseType(DataBaseType.Master);
        }else {
            DataSourceType.setDataBaseType(DataBaseType.Slave);
        }

    }

    /**
     * 注解所在的方法执行完后，清除数据源的配置
     * @param point
     * @param dataSource
     */
    @After("@annotation(dataSource)")
    //@After(value = "@within(com.qiuhuu.test.annotation.DataSource) && @args(dataSource)")
    public void restoreDataSource(JoinPoint point, DataSource dataSource) {
        DataSourceType.clearDataBaseType();
    }
}
