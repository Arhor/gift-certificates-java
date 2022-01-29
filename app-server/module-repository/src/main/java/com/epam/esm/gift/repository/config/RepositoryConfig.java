package com.epam.esm.gift.repository.config;

import java.time.Clock;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration(proxyBeanMethods = false)
@ComponentScan("com.epam.esm.gift.repository")
@EntityScan("com.epam.esm.gift.repository.model")
@EnableJpaAuditing(modifyOnCreate = false, dateTimeProviderRef = "currentDateTimeUpToMillisDateTimeProvider")
@EnableTransactionManagement
public class RepositoryConfig {

    @Bean
    public DateTimeProvider currentDateTimeUpToMillisDateTimeProvider() {
        return () -> {
            var clock = Clock.systemUTC();
            var currentDateTime = LocalDateTime.now(clock);
            return Optional.of(currentDateTime.truncatedTo(ChronoUnit.MILLIS));
        };
    }
}
