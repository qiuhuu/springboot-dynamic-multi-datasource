package com.qiuhuu.test.config;

import com.qiuhuu.test.datasource.DynamicDataSource;
import com.qiuhuu.test.type.DataBaseType;
import com.qiuhuu.test.type.DataSourceType;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : qiuhuu
 * @date : 2020-11-10 17:10
 */
@Configuration
public class DataSourceConfig {

    @Bean(name = "masterDataSourceProperties")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSourceProperties masterDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "masterDataSource")
    @Qualifier("masterDataSource")
    @Primary
    public DataSource masterDataSource() {
        return masterDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


    @Bean(name = "slaveDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSourceProperties slaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "slaveDataSource")
    @Qualifier("slaveDataSource")
    public DataSource slaveDataSource() {
        return slaveDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Bean
    public DynamicDataSource dynamicDatasource(
            @Qualifier("masterDataSource") DataSource masterDataSource,
            @Qualifier("slaveDataSource") DataSource slaveDataSource
    ){
        Map<Object,Object> targetDatasource=new HashMap<Object,Object>(2){{
            put(DataBaseType.Master,masterDataSource);
            put(DataBaseType.Slave,slaveDataSource);
        }};
        DynamicDataSource dynamicDatasource = new DynamicDataSource();
        dynamicDatasource.setTargetDataSources(targetDatasource);
        dynamicDatasource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDatasource;
    }
}
