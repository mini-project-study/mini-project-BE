package com.teamy.mini.jwt;

import com.teamy.mini.domain.Member;
import com.teamy.mini.error.ErrorCode;
import com.teamy.mini.repository.MemberRepository;
import com.teamy.mini.security.MemberAccount;
import com.teamy.mini.service.RedisTestService;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtAuthenticationProvider {

    //나중에 수정 필요
    private static String secret = Base64.getEncoder().encodeToString("bWluaS1wcm9qZWN0LXRlYW15LXllYW5hLWRheWVh".getBytes());
    private final Long accessTokenValidate;
    private final Long refreshTokenValidate;
    private static final String AUTHORITIES_KEY = "auth";
    private final RedisTestService redisTestService;
    private final MemberRepository memberRepository;

    //단위 : 밀리초 - 3600000 (1시간으로 늘림)
    public JwtAuthenticationProvider(/*@Value("${jwt.secret}") String secret,*/
                            /*@Value("${jwt.access-token-validity-in-seconds}") Long accessTokenValidate,
                            @Value("${jwt.refresh-token-validity-in-seconds}") Long refreshTokenValidate*/
            @Value("3600000") Long accessTokenValidate, @Value("7200000") Long refreshTokenValidate, RedisTestService redisTestService, MemberRepository memberRepository) {
        //this.secret = secret;
        this.accessTokenValidate = accessTokenValidate;
        this.refreshTokenValidate = refreshTokenValidate;
        this.redisTestService = redisTestService;
        this.memberRepository = memberRepository;
    }

    public long getTokenExpire(String token){
        token = token.substring(7);
        Claims claims = Jwts
                .parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        Date expiration = claims.get("exp", Date.class);
        Date today = new Date();

        return expiration.getTime() - today.getTime();
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
        log.info("getAuthentication : token 사용한 사용자 정보 조회");
        Claims claims = Jwts
                .parser().setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        List<SimpleGrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        //User principal = new User(claims.getSubject(), "", authorities);
        //log.info("claims getSubject : " + claims.getSubject()); // email이 들어있음
        // 예나 : 다른데서 멤버 id 꺼내 쓰기 위해 MemberAccount로 저장
        Member member = memberRepository.findByEmail(claims.getSubject());
        MemberAccount principal = new MemberAccount(member, authorities);

        // 이건 안됨
        // MemberAccount principal = (MemberAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("token 조회 pringcipal : {}, token : {}, authoritied : {} ", principal, token, authorities);
        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateToken(String token, ServletRequest request) {
        log.info("token 이 널이 아니라니 : {}", token );
        if (token == null || !StringUtils.hasText(token)) {
            request.setAttribute("Exception", ErrorCode.TOKEN_NOT_FOUND);
            return false;
        }
        if(redisTestService.getRedisStringValue(token) != null){
            // 로그아웃 된 토큰이 들어옴.
            request.setAttribute("Exception", ErrorCode.TOKEN_IN_BLACKLIST);
            return false;
        }

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
