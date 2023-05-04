package com.sct.rest.api.configuration;

import com.sct.rest.api.security.interseptor.SecurityInterceptor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("#{'${cors.registry.allowed-origins}'.split(', ')}")
    private String[] origins;

    @Value("#{'${cors.registry.methods}'.split(', ')}")
    private String[] methods;

    private final SecurityInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interceptor).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        log.info("CORS config: allowed-origins={}", Arrays.toString(origins));
        registry.addMapping("/**").allowedMethods(methods).allowedHeaders("*").allowedOrigins(origins);
    }
}
