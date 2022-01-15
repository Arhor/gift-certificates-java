package com.epam.esm.gift.repository;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;

import com.epam.esm.gift.config.FlyWayConfig;

@Import(FlyWayConfig.class)
@Configuration(proxyBeanMethods = false)
@ComponentScan("com.epam.esm.gift.repository")
@EnableTransactionManagement
public class TestDatabaseConfig {

    @Container
    private static final PostgreSQLContainer<?> DB = new PostgreSQLContainer<>("postgres:11.7");

    static {
        DB.start();
    }

    @Bean
    public DataSource dataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setUrl(DB.getJdbcUrl());
        dataSource.setUsername(DB.getUsername());
        dataSource.setPassword(DB.getPassword());
        dataSource.setDriverClassName(DB.getDriverClassName());
        return dataSource;
    }

    @Bean
    public TransactionManager transactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}