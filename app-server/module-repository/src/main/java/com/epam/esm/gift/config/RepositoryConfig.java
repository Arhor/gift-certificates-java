package com.epam.esm.gift.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Import(FlyWayConfig.class)
@Configuration(proxyBeanMethods = false)
@ComponentScan("com.epam.esm.gift.repository")
@EnableTransactionManagement
public class RepositoryConfig {

    @Bean
    public DataSource dataSource() {
        return new HikariDataSource(new HikariConfig("/db/database.properties"));
    }

    @Bean
    public PlatformTransactionManager platformTransactionManager(final DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public NamedParameterJdbcTemplate jdbcTemplate(final DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }
}
