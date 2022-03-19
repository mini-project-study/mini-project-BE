package com.teamy.mini.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

//@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); //
        config.addAllowedOrigin("*"); //모든 ip에 응답 허용
        config.addAllowedHeader("*"); //모든 header에 응답 허용
        config.addAllowedMethod("*"); //모든 post, get, put, delete, patch 요청 허용
        source.registerCorsConfiguration("/**", config); // (/**로 들어오는) 모든 요청은 config를 타도록
        return new CorsFilter(source);
    }
}
