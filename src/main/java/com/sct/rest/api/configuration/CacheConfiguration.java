package com.sct.rest.api.configuration;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfiguration {

    public static final String PRICE_CACHE_NAME = "prices";

    @Bean
    public CacheManager cacheManager() {
        return new ConcurrentMapCacheManager(PRICE_CACHE_NAME);
    }
}
