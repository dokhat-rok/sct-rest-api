package com.sct.rest.api.security;

import com.sct.rest.api.model.dto.security.CallContext;
import com.sct.rest.api.model.enums.Role;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

@Component
@Slf4j
public class JwtFilter extends GenericFilterBean {


    @Value("${jwt.secret}")
    private String secret;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader = ((HttpServletRequest)request).getHeader(HttpHeaders.AUTHORIZATION);
        CallContext context = new CallContext();
        if (authorizationHeader.equals("Bearer")) {
            context.setAuthenticated(true);
            context.setCustomerRoles(Set.of(Role.UNKNOWN));
            SecurityContextHolder.getContext().setAuthentication(context);
            filterChain.doFilter(request, response);
            return;
        }
        Claims claims = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(authorizationHeader.substring(7))
                .getBody();

        context.setCustomerLogin(claims.get("login", String.class));
        context.setCustomerRoles(Set.of(Role.valueOf(claims.get("role", String.class))));
        context.setAuthenticated(true);
        log.info("Пользователь [{}], роль [{}]", context.getCustomerLogin(), context.getCustomerRoles());
        log.info("{}", Role.ADMIN.name());
        SecurityContextHolder.getContext().setAuthentication(context);
        SecurityContext.set(context);
        filterChain.doFilter(request, response);
    }
}
