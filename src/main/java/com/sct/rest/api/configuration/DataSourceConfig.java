package com.sct.rest.api.configuration;

import liquibase.integration.spring.SpringLiquibase;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories("com.sct.rest.api.repository")
@RequiredArgsConstructor
public class DataSourceConfig {

    @Value("${liquibase.changelog-file}")
    private String changeLogFile;

    private final DataSource dataSource;

    @ConditionalOnProperty(value = "spring.liquibase.enabled", havingValue = "true")
    @Bean
    public SpringLiquibase initLiquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setDataSource(dataSource);
        liquibase.setChangeLog(changeLogFile);
        return liquibase;
    }
}
