package com.teamy.mini.jwt;

import com.teamy.mini.error.ErrorCode;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import javax.servlet.ServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtAuthenticationProvider {

    //나중에 수정 필요
    private static String secret = Base64.getEncoder().encodeToString("bWluaS1wcm9qZWN0LXRlYW15LXllYW5hLWRheWVh".getBytes());
    private final Long accessTokenValidate;
    private final Long refreshTokenValidate;
    private static final String AUTHORITIES_KEY = "auth";

    public JwtAuthenticationProvider(/*@Value("${jwt.secret}") String secret,*/
                            /*@Value("${jwt.access-token-validity-in-seconds}") Long accessTokenValidate,
                            @Value("${jwt.refresh-token-validity-in-seconds}") Long refreshTokenValidate*/
    @Value("86400") Long accessTokenValidate, @Value("1209600") Long refreshTokenValidate) {
        //this.secret = secret;
        this.accessTokenValidate = accessTokenValidate;
        this.refreshTokenValidate = refreshTokenValidate;
    }

    public String createAccessToken(Authentication authentication) {
        log.info("토큰 생성");
        
        //권한
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validate = new Date(now + this.accessTokenValidate);
        
        //권한추가해
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(authentication.getName())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, secret)
                .setExpiration(validate)
                .compact();

    }

//    public String createRefreshToken() {
//
//    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        List<SimpleGrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        log.info("token 조회 pringcipal : {}, token : {}, authoritied : {} ", principal, token, authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token, ServletRequest request) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (SignatureException | MalformedJwtException e) { //io.jsonwebtoken.security.SignatureException
            log.info("잘못된 JWT 서명");
            request.setAttribute("Exception", ErrorCode.TOKEN_MALFORMED_JWT);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰");
            request.setAttribute("Exception", ErrorCode.TOKEN_EXPIRED_JWT);
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰");
            request.setAttribute("Exception", ErrorCode.TOKEN_UNSUPPORTED_JWT);
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못됨");
            request.setAttribute("Exception", ErrorCode.TOKEN_ILLEGAL_JWT);
        }


        return false;
    }

}
