package com.sct.rest.api.service.client;

import com.sct.rest.api.model.dto.security.JwtDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;

@Component
public class TokenClient {

    @Value("${auth.service.base.url}")
    private String baseUrl;

    private WebClient webClient;

    @PostConstruct
    private void init() {
        webClient = WebClient.create(baseUrl);
    }

    public JwtDto getToken(String login, String password) {
        String uri = "/auth";
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(uri)
                        .queryParam("login", login)
                        .queryParam("password", password)
                        .build())
                .retrieve()
                .bodyToMono(JwtDto.class)
                .block();
    }
}
