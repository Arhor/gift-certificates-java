package com.epam.esm.gift.repository

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.transaction.annotation.EnableTransactionManagement
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import javax.sql.DataSource

@Import(FlyWayConfig::class)
@Configuration(proxyBeanMethods = false)
@ComponentScan("com.epam.esm.gift.repository")
@EnableTransactionManagement
internal class TestDatabaseConfig {

    companion object {
        @Container
        private val db: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:12").also { it.start() }
    }

    @Bean
    fun dataSource() = DriverManagerDataSource().apply {
        url = db.jdbcUrl
        username = db.username
        password = db.password
        setDriverClassName(db.driverClassName)
    }

    @Bean
    fun transactionManager(dataSource: DataSource) = DataSourceTransactionManager(dataSource)

    @Bean
    fun jdbcTemplate(dataSource: DataSource) = NamedParameterJdbcTemplate(dataSource)
}
