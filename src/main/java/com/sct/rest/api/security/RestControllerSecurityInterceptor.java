package com.sct.rest.api.security;

import com.sct.rest.api.service.ServiceRuntimeException;
import com.sct.rest.api.service.ErrorCodeEnum;
import com.sct.rest.api.model.entity.enums.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.Claims;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@RequiredArgsConstructor
@Slf4j
public class RestControllerSecurityInterceptor extends HandlerInterceptorAdapter {

    @Value("${jwt.secret}")
    private String secret;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        try{
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authorizationHeader).getBody();
            CallContext callContext = CallContext.builder()
                    .customerLogin(claims.get("login", String.class))
                    .customerPassword(claims.get("password", String.class))
                    .customerRole(Role.getRole(claims.get("role", String.class)))
                    .build();
            log.info("Пользователь [{}], роль [{}]", callContext.getCustomerLogin(), callContext.getCustomerRole());
            SecurityContext.set(callContext);
        }
        catch (Exception e){
            throw new ServiceRuntimeException(ErrorCodeEnum.ERROR_AUTHORIZATION, new Throwable());
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
        SecurityContext.clear();
    }
}
