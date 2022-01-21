package com.epam.esm.gift.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration(proxyBeanMethods = false)
@ComponentScan(
    basePackages = {"com.epam.esm.gift"},
    excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = {
            EnableWebMvc.class,
            RestController.class,
            RestControllerAdvice.class,
        }),
        @Filter(type = FilterType.REGEX, pattern = {
            "com.epam.esm.gift.web.*"
        })
    }
)
public class RootConfig {
}
