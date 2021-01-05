package com.qiuhuu.test.config.jpa;


import com.qiuhuu.test.config.DataSourceConfig;
import com.qiuhuu.test.datasource.DynamicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateProperties;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateSettings;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import java.util.Map;

/**
 * @author : qiuhuu
 * @date : 2020-11-10 17:10
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "jpaEntityManagerFactory",
        transactionManagerRef = "jpaTransactionManager",
        basePackages = { PrimaryDataSourceConfig.REPO_PACKAGE } // 设置 Repository 所在位置
)
public class PrimaryDataSourceConfig {

    static final String REPO_PACKAGE = "com.qiuhuu.test.dao.jpa";
    static final String ENTITY_PACKAGE = "com.qiuhuu.test.entity.jpa";

    @Autowired
    private DynamicDataSource dynamicDataSource;

    @Resource
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Primary
    @Bean(name = "jpaEntityManager")
    public EntityManager jpaEntityManager(EntityManagerFactoryBuilder builder) {
        return jpaEntityManagerFactory(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "jpaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean jpaEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());
        DataSourceConfig.hikariDataSourceInfo(dynamicDataSource,"jpa");
        return builder.dataSource(dynamicDataSource)
                .properties(properties)
                .persistenceUnit("jpaPersistenceUnit")
                .packages(ENTITY_PACKAGE)
                .build();
    }

    @Primary
    @Bean(name = "jpaTransactionManager")
    public PlatformTransactionManager jpaTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(jpaEntityManagerFactory(builder).getObject());
    }
}
