package com.epam.esm.gift.localization.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration(proxyBeanMethods = false)
public class MessageSourceConfig {

    public static final String ERROR_MESSAGES_BEAN = "errorMessages";

    @Bean(name = ERROR_MESSAGES_BEAN)
    public MessageSource errorMessages() {
        var messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("error-messages");
        return messageSource;
    }
}
