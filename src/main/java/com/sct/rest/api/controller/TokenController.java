package com.sct.rest.api.controller;

import com.sct.rest.api.model.dto.security.JwtDto;
import com.sct.rest.api.service.client.TokenClient;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/token")
@RequiredArgsConstructor
public class TokenController {

    private final TokenClient tokenClient;

    @GetMapping
    public ResponseEntity<JwtDto> getToken(String login, String password) {
        JwtDto jwt = tokenClient.getToken(login, password);
        return ResponseEntity.ok(jwt);
    }
}
