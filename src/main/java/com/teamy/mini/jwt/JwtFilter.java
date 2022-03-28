package com.teamy.mini.jwt;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
@Component
public class JwtFilter extends GenericFilterBean {

    public static final String AUTHORIZTION_HADER = "Authorization";

    private JwtAuthenticationProvider jwtAuthenticationProvider;

    JwtFilter(JwtAuthenticationProvider jwtAuthenticationProvider) {
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
    }

    //doFilter : 토큰이 유효하면 SecurityContext에 정보 담음
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String jwt = resolveToken(httpServletRequest);
        String requestURI = httpServletRequest.getRequestURI();

        log.info("httpServletRequest header : {}", httpServletRequest.getHeader(AUTHORIZTION_HADER));
        log.info("jwt : {}", jwt);
//        if(StringUtils.hasText(jwt) && jwtAuthenticationProvider.validateToken(jwt, servletRequest)) {
//            Authentication authentication = jwtAuthenticationProvider.getAuthentication(jwt);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            log.info("인증 ㅇㅇ SecurityContextHolder에 {} 인증 정보 저장, URI : {}", authentication.getName(), requestURI);
//        } else {
//
//            log.info("유효한 토큰 ㄴㄴ, URI : {}", requestURI);
//            if(jwt == null){
//                log.info("jwt없당");
//            }
//        }

            if (jwtAuthenticationProvider.validateToken(jwt, servletRequest)) {
                Authentication authentication = jwtAuthenticationProvider.getAuthentication(jwt);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("인증 ㅇㅇ SecurityContextHolder에 {} 인증 정보 저장, URI : {}", authentication.getName(), requestURI);
            } else {

                log.info("유효한 토큰 ㄴㄴ, URI : {}", requestURI);
                if (jwt == null) {
                    log.info("jwt없당");
                }
            }

            filterChain.doFilter(servletRequest, servletResponse);
        }

        private String resolveToken (HttpServletRequest request){
            String bearerToken = request.getHeader(AUTHORIZTION_HADER);
            if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
                return bearerToken.substring(7);
            }

            return null;
        }
    }
