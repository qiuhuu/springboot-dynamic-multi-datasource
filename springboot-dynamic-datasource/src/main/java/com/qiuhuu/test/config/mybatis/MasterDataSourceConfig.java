package com.qiuhuu.test.config.mybatis;

import com.qiuhuu.test.datasource.DynamicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @author : qiuhuu
 * @date : 2020-11-10 17:21
 */
@Configuration
@MapperScan(basePackages = MasterDataSourceConfig.PACKAGE, sqlSessionFactoryRef = "mybatisSqlSessionFactory")
public class MasterDataSourceConfig {
    /**
     *精确到master目录和其他数据源隔离
     */
    static final String PACKAGE = "com.qiuhuu.test.dao.mybatis";
    static final String MAPPER_LOCATION = "classpath:mybatis/mapper/*.xml";

    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Bean(name = "mybatisTransactionManager")
    @Primary
    public DataSourceTransactionManager mybatisTransactionManager() {
        return new DataSourceTransactionManager(dynamicDataSource);
    }

    @Bean(name = "mybatisSqlSessionFactory")
    @Primary
    public SqlSessionFactory mybatisSqlSessionFactory(@Qualifier("dynamicDatasource") DataSource dynamicDatasource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dynamicDatasource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(MasterDataSourceConfig.MAPPER_LOCATION));
        return sessionFactory.getObject();
    }
}
