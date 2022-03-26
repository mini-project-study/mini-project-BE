package com.teamy.mini.security;

import com.teamy.mini.jwt.JwtAccessDeniedHandler;
import com.teamy.mini.jwt.JwtAuthenticationEntryPoint;
import com.teamy.mini.jwt.JwtAuthenticationProvider;
import com.teamy.mini.jwt.JwtSecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;


@Configuration
@RequiredArgsConstructor
@EnableWebSecurity //Spring security filter를 스프링 필터 체인에 등록
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final MemberDetailsService memberDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;



//    @Bean
//    public AuthenticationProvider customAuthenticationProvider() {
//        return new CustomAuthenticationProvider();
//    }
//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(customAuthenticationProvider());
//    }

//
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.authenticationProvider(daoAuthenticationProvider());
//    }
//
//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }
//
//    public DaoAuthenticationProvider daoAuthenticationProvider() {
//        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
//        daoAuthenticationProvider.setUserDetailsService(memberDetailsService);
//        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder);
//        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
//        return daoAuthenticationProvider;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                cors().
                and().
                csrf().disable(). // 사이트 간 요청 위조 설정(request를 가로채 변조하고, 변조된 request를 백서버에 보내는 것) Rest API 서버이기때문에 CSRF 처리를 해제합니다.
                formLogin().disable(). //REST API - 기본 로그인창 사용하지 않으므로 disable
                httpBasic().disable(). //REST API - 기본 인증 로그인 이용하지 않으므로 disable
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS); //session 사옹하지 않으므로 STATELESS

        http.
                exceptionHandling().
                authenticationEntryPoint(jwtAuthenticationEntryPoint).
                accessDeniedHandler(jwtAccessDeniedHandler).
                and().
                apply(new JwtSecurityConfig(jwtAuthenticationProvider));



        http.authorizeHttpRequests()
                //.antMatchers("/login", "/join", "/hello").permitAll();
                .antMatchers("/login", "/join").permitAll()
                .antMatchers("/hello").authenticated(); //로그인한 사용자만 들어갈 수 있게
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOriginPatterns(Arrays.asList("*")); //프론트와 연결 테스트 실패 : Spring Boot에서 CORS 설정 시 .allowCredentials(true)와 .allowedOrigins("*")를 동시에 사용할 수 없도록 업데이트 됨 > .allowedOrigins("*") -> .allowed/originPatterns("*")로 변경하여 해결
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        //configuration.addExposedHeader("Authorization");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }


}
