package com.sct.rest.api.security.interseptor;

import com.sct.rest.api.model.dto.security.CallContext;
import com.sct.rest.api.model.enums.Role;
import com.sct.rest.api.security.SecurityContext;
import com.sct.rest.api.exception.enums.ErrorCodeEnum;
import com.sct.rest.api.exception.ServiceRuntimeException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class SecurityInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (HttpMethod.OPTIONS.matches(request.getMethod())) {
            return true;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authorizationHeader).getBody();
            CallContext callContext = CallContext.builder()
                    .customerLogin(claims.get("login", String.class))
                    .customerPassword(claims.get("password", String.class))
                    .customerRole(Role.valueOf(claims.get("role", String.class)))
                    .build();
            log.info("Пользователь [{}], роль [{}]", callContext.getCustomerLogin(), callContext.getCustomerRole());
            SecurityContext.set(callContext);
        } catch (Exception e) {
            throw new ServiceRuntimeException(ErrorCodeEnum.ERROR_AUTHORIZATION, new Throwable());
        }
        return true;
    }

    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            @Nullable Exception ex) {
        SecurityContext.clear();
    }
}
