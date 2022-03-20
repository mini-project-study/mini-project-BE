package com.teamy.mini.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity //Spring security filter를 스프링 필터 체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //private CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                addFilterBefore(new JwtAuthenticationFilter(), BasicAuthenticationFilter.class);
        http.
                csrf().disable();
        http.
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) //session 사용하지 않겠다는 의미(jwt를 사용하므로), 스프링 시큐리티 생성x 기존것 사용도x
                .and()
                //.addFilter(corsFilter)
                .formLogin().disable() //
                .httpBasic().disable() //http 기본 인증 방식 비활성화
                .authorizeHttpRequests()
                .anyRequest().permitAll();

                //.antMatchers("/**").authenticated(); //로그인한 사용자만 들어갈 수 있게
    }
}
