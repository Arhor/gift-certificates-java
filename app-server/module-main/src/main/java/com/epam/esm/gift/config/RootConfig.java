package com.epam.esm.gift.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

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

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.ENGLISH);
        return localeResolver;
    }
}
