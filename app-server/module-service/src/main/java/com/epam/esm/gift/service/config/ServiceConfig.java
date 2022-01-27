package com.epam.esm.gift.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration(proxyBeanMethods = false)
@ComponentScan("com.epam.esm.gift.service")
public class ServiceConfig {
}
