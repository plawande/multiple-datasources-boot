package com.plawande.multipledatasources.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager",
        basePackages = {"com.plawande.multipledatasources.repository.mysql"})
//@ConfigurationProperties(prefix = "spring.mysql.datasource")
public class MySQLdbConfig {  //extends HikariConfig

    @Primary
    @Bean(name = "datasource")
    @ConfigurationProperties(prefix = "spring.mysql.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder
                .create()
                .build();
        /*return new HikariDataSource(this);*/
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(@Qualifier("datasource") DataSource dataSource,
                                                                           EntityManagerFactoryBuilder entityManagerFactoryBuilder) {
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.MySQL5Dialect");

        return entityManagerFactoryBuilder.dataSource(dataSource)
                .packages("com.plawande.multipledatasources.model.mysql")
                .properties(properties)
                .persistenceUnit("MySQL")
                .build();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(@Qualifier("entityManagerFactory") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}

//https://scattercode.co.uk/2016/01/05/multiple-databases-with-spring-boot-and-spring-data-jpa/
