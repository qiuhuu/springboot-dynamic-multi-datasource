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

    /********master数据源配置************/
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
        return buildHikariDataSource(properties);
    }
    /********master数据源配置结束************/


    /********slave数据源配置************/
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
        return buildHikariDataSource(properties);

        //return slaveDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
    /********slave数据源配置结束************/


    /********node数据源配置************/
    @Bean(name = "nodeDataSourceProperties")
    @ConfigurationProperties(prefix = "spring.datasource.node")
    public DataSourceProperties nodeDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "nodeDataSource")
    @Qualifier("nodeDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.node.hikari")
    public DataSource nodeDataSource() {
        DataSourceProperties properties = nodeDataSourceProperties();
        return buildHikariDataSource(properties);
    }
    /********node数据源配置结束************/

    public static void hikariDataSourceInfo(DynamicDataSource dataSource,String framework){
        Map<Object, DataSource> resolvedDataSources = dataSource.getResolvedDataSources();
        Map<Object, HikariDataSource> objectHikariDataSourceMap = Maps.transformEntries(resolvedDataSources, (k, v) -> new HikariDataSource((HikariConfig) v));
        Iterator<Map.Entry<Object, HikariDataSource>> iterator = objectHikariDataSourceMap.entrySet().iterator();
        while (iterator.hasNext()){
            HikariDataSource hd = iterator.next().getValue();
            String info =
                    "\n\t"+framework+"-HikariCP连接池配置\n" +
                    "\n\t连接池名称：" + hd.getPoolName() +
                    "\n\t最小空闲连接数：" + hd.getMinimumIdle() +
                    "\n\t最大连接数：" + hd.getMaximumPoolSize() +
                    "\n\t连接超时时间：" + hd.getConnectionTimeout() + "ms" +
                    "\n\t空闲连接超时时间：" + hd.getIdleTimeout() + "ms" +
                    "\n\t连接最长生命周期：" + hd.getMaxLifetime() + "ms";
            log.info(info);
        }

    }

    /**
     * 构建hikari数据源
     * @param properties 数据源配置
     * @return 构建完成的数据源
     */
    public DataSource buildHikariDataSource(DataSourceProperties properties){
        HikariDataSource dataSource = properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
        if (StringUtils.hasText(properties.getName())) {
            dataSource.setPoolName(properties.getName());
        }
        return dataSource;
    }

    /**
     * 动态数据源配置
     * 每增加一个数据源，配置一个数据源
     * @param masterDataSource
     * @param slaveDataSource
     * @param nodeDataSource
     * @return
     */
    @Bean
    public DynamicDataSource dynamicDatasource(
            @Qualifier("masterDataSource") DataSource masterDataSource,
            @Qualifier("slaveDataSource") DataSource slaveDataSource,
            @Qualifier("nodeDataSource") DataSource nodeDataSource
    ){
        Map<Object,Object> targetDatasource=new HashMap<Object,Object>(3){{
            put(DataBaseType.Master,masterDataSource);
            put(DataBaseType.Slave,slaveDataSource);
            put(DataBaseType.Node,nodeDataSource);
        }};
        DynamicDataSource dynamicDatasource = new DynamicDataSource();
        dynamicDatasource.setTargetDataSources(targetDatasource);
        dynamicDatasource.setDefaultTargetDataSource(masterDataSource);
        return dynamicDatasource;
    }
}
