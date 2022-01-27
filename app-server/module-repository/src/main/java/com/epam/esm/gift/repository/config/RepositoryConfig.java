package com.epam.esm.gift.repository.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration(proxyBeanMethods = false)
@ComponentScan("com.epam.esm.gift.repository")
@EnableTransactionManagement
public class RepositoryConfig {
}
