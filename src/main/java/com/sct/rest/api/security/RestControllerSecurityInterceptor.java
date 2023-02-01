package com.sct.rest.api.security;

import com.sct.rest.api.service.ServiceRuntimeException;
import com.sct.rest.api.service.ErrorCodeEnum;
import com.sct.rest.api.model.entity.enums.Role;
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
@Slf4j
public class RestControllerSecurityInterceptor extends HandlerInterceptorAdapter {

    @Value("${jwt.secret}")
    private String secret;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception{

        if(HttpMethod.OPTIONS.matches(request.getMethod())){
            return true;
        }

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        System.out.println(authorizationHeader);
        String[] authPath = authorizationHeader.split(" ");

        try{

            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authPath[1]).getBody();
            CallContext callContext = CallContext.builder().userId(Long.parseLong(claims.getSubject())).userLogin(claims.get("login", String.class)).userRole(Role.getRole(claims.get("role", String.class))).userBalance(claims.get("balance", Long.class)).build();
            log.info("Пользователь [{}], роль [{}]", callContext.getUserLogin(), callContext.getUserRole());
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
