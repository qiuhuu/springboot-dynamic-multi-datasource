package com.qiuhuu.test.config.jpa;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
import javax.sql.DataSource;
import java.util.Map;

/**
 * @author : qiuhuu
 * @date : 2020-11-10 17:10
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "primaryEntityManagerFactory",
        transactionManagerRef = "primaryTransactionManager",
        basePackages = { PrimaryDataSourceConfig.REPO_PACKAGE } // 设置 Repository 所在位置
)
public class PrimaryDataSourceConfig {

    static final String REPO_PACKAGE = "com.qiuhuu.test.dao.jpa.primary";
    static final String ENTITY_PACKAGE = "com.qiuhuu.test.entity.jpa.primary";

    @Qualifier("masterDataSource")
    private DataSource primaryDataSource;

    @Resource
    private JpaProperties jpaProperties;

    @Autowired
    private HibernateProperties hibernateProperties;

    @Primary
    @Bean(name = "primaryEntityManager")
    public EntityManager primaryEntityManager(EntityManagerFactoryBuilder builder) {
        return primaryEntityManagerFactory(builder).getObject().createEntityManager();
    }

    @Primary
    @Bean(name = "primaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());

        return builder.dataSource(primaryDataSource)
                .properties(properties)
                .persistenceUnit("primaryPersistenceUnit")
                .packages(ENTITY_PACKAGE)
                .build();
    }

    @Primary
    @Bean(name = "primaryTransactionManager")
    public PlatformTransactionManager primaryTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(primaryEntityManagerFactory(builder).getObject());
    }
}
