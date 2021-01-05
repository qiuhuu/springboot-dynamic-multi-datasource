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
        entityManagerFactoryRef = "secondaryEntityManagerFactory",
        transactionManagerRef = "secondaryTransactionManager",
        basePackages = { SecondaryDataSourceConfig.REPO_PACKAGE } // 设置 Repository 所在位置
)
public class SecondaryDataSourceConfig {
    static final String REPO_PACKAGE = "com.qiuhuu.test.dao.jpa.secondary";
    static final String ENTITY_PACKAGE = "com.qiuhuu.test.entity.jpa.secondary";

    @Resource
    @Qualifier("slaveDataSource")
    private DataSource secondaryDataSource;

    @Resource
    private JpaProperties jpaProperties;
    @Autowired
    private HibernateProperties hibernateProperties;

    @Bean(name = "secondaryEntityManager")
    public EntityManager secondaryEntityManager(EntityManagerFactoryBuilder builder) {
        return secondaryEntityManagerFactory(builder).getObject().createEntityManager();
    }

    @Bean(name = "secondaryEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean secondaryEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String, Object> properties = hibernateProperties.determineHibernateProperties(
                jpaProperties.getProperties(), new HibernateSettings());

        return builder.dataSource(secondaryDataSource)
                .properties(properties)
                .persistenceUnit("primaryPersistenceUnit")
                .packages(ENTITY_PACKAGE)
                .build();
    }

    @Bean(name = "secondaryTransactionManager")
    public PlatformTransactionManager secondaryTransactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(secondaryEntityManagerFactory(builder).getObject());
    }
}
