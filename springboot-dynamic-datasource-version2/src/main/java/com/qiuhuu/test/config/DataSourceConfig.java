package com.qiuhuu.test.config;

import com.google.common.collect.Maps;
import com.qiuhuu.test.datasource.DynamicDataSource;
import com.qiuhuu.test.type.DataBaseType;
import com.qiuhuu.test.type.DataSourceType;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author : qiuhuu
 * @date : 2020-11-10 17:10
 */
@Slf4j
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
    @ConfigurationProperties(prefix = "spring.datasource.master.hikari")
    public DataSource masterDataSource() {
        DataSourceProperties properties = masterDataSourceProperties();
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }


    @Bean(name = "slaveDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSourceProperties slaveDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "slaveDataSource")
    @Qualifier("slaveDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.slave.hikari")
    public DataSource slaveDataSource() {

        DataSourceProperties properties = slaveDataSourceProperties();
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;

        //return slaveDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }


    public static void hikariDataSourceInfo(DynamicDataSource dataSource){
        Map<Object, DataSource> resolvedDataSources = dataSource.getResolvedDataSources();
        Map<Object, HikariDataSource> objectHikariDataSourceMap = Maps.transformEntries(resolvedDataSources, (k, v) -> new HikariDataSource((HikariConfig) v));
        Iterator<Map.Entry<Object, HikariDataSource>> iterator = objectHikariDataSourceMap.entrySet().iterator();
        while (iterator.hasNext()){
            HikariDataSource hd = iterator.next().getValue();
            String info =
                    "\n\n\tHikariCP连接池配置\n" +
                    "\n\t连接池名称：" + hd.getPoolName() +
                    "\n\t最小空闲连接数：" + hd.getMinimumIdle() +
                    "\n\t最大连接数：" + hd.getMaximumPoolSize() +
                    "\n\t连接超时时间：" + hd.getConnectionTimeout() + "ms" +
                    "\n\t空闲连接超时时间：" + hd.getIdleTimeout() + "ms" +
                    "\n\t连接最长生命周期：" + hd.getMaxLifetime() + "ms";
            log.info(info);
        }

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
