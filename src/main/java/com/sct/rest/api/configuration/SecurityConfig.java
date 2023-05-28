package com.sct.rest.api.configuration;

import com.sct.rest.api.model.enums.Role;
import com.sct.rest.api.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final String[] WHITE_LIST = {
            "/swagger-ui.html",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v1/token"
    };
    private final String[] AUTH_LIST = {
            "/v1/customer/current/**",
            "/v1/parking/all",
            "/v1/transport/all/filter",
            "/v1/trip/**",
            "/v1/rent/current/all",
            "/v1/route/point"
    };
    private final String[] AUTH_ROLE_LIST = {Role.ADMIN.name(), Role.MANAGER.name(), Role.USER.name()};
    private final String[] ADMIN_ROLE_LIST = {Role.ADMIN.name(), Role.MANAGER.name()};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors().and()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(WHITE_LIST).permitAll()
                .antMatchers(AUTH_LIST).hasAnyRole(AUTH_ROLE_LIST)
                .antMatchers(HttpMethod.GET, "/v1/price/**").hasAnyRole(AUTH_ROLE_LIST)
                .anyRequest().hasAnyRole(ADMIN_ROLE_LIST)
                .and().addFilterAfter(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .headers().httpStrictTransportSecurity().disable()
                .and().build();
    }
}