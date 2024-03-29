package com.sct.rest.api.service.impl;

import com.sct.rest.api.model.dto.PriceDto;
import com.sct.rest.api.model.enums.TransportType;
import com.sct.rest.api.service.PriceService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static com.sct.rest.api.configuration.CacheConfiguration.PRICE_CACHE_NAME;

@Service
@CacheConfig(cacheNames = {PRICE_CACHE_NAME})
@PropertySource(value = "classpath:/price.yml")
public class PriceServiceImpl implements PriceService {

    @Value("${initialPriceForBicycle}")
    private BigDecimal initialPriceForBicycle;

    @Value("${initialPriceForScooter}")
    private BigDecimal initialPriceForScooter;

    @Value("${pricePerMinuteForBicycle}")
    private BigDecimal pricePerMinuteForBicycle;

    @Value("${pricePerMinuteForScooter}")
    private BigDecimal pricePerMinuteForScooter;

    @Override
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

    @Override
    @CachePut(key = "#type")
    public PriceDto setPrice(TransportType type, PriceDto price) {
        PriceDto actualPrice = this.getActualPrice(type);
        if(price.getInit() != null) actualPrice.setInit(price.getInit());
        if(price.getPerMinute() != null) actualPrice.setPerMinute(price.getPerMinute());
        return actualPrice;
    }
}
