package com.sct.rest.api.service;

import com.sct.rest.api.model.dto.PriceDto;
import com.sct.rest.api.model.enums.TransportType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.sct.rest.api.configuration.CacheConfiguration.PRICE_CACHE_NAME;

@Service
@CacheConfig(cacheNames = {PRICE_CACHE_NAME})
@PropertySource(value = "classpath:/price.yml")
public class PriceService {

    @Value("${initialPriceForBicycle}")
    private BigDecimal initialPriceForBicycle;

    @Value("${initialPriceForScooter}")
    private BigDecimal initialPriceForScooter;

    @Value("${pricePerMinuteForBicycle}")
    private BigDecimal pricePerMinuteForBicycle;

    @Value("${pricePerMinuteForScooter}")
    private BigDecimal pricePerMinuteForScooter;

    @Cacheable(key = "#type")
    public PriceDto getActualPrice(TransportType type) {
        return type == TransportType.BICYCLE ?
                PriceDto.builder()
                        .init(initialPriceForBicycle.longValue())
                        .perMinute(pricePerMinuteForBicycle.longValue())
                        .build() :
                PriceDto.builder()
                        .init(initialPriceForScooter.longValue())
                        .perMinute(pricePerMinuteForScooter.longValue())
                        .build();
    }
}
