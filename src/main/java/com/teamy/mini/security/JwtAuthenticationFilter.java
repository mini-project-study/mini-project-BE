package com.teamy.mini.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.teamy.mini.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    // /login 요청 들어오면 실행될 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("----- JwtAuthenticationFilter 로그인 시도 중"); //localhost:8080/login 했을 때 이거 나와야 함

        //email, password 받기
        try {
//            BufferedReader br = request.getReader();
//            String input = null;
//            while((input = br.readLine()) != null) {
//                log.info(input);
//            }
            
            //json 데이터 파싱
            ObjectMapper om = new ObjectMapper();
            Member member = om.readValue(request.getInputStream(), Member.class);

            //토큰 만들기
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(member.getEmail(), member.getPassword());

            //정상인지 확인. authenticationManager로 로그인 시도 > PrincipalDetailService의 loadUserByUsername 호출됨
            //인증이 성공적으로 이뤄졌다면 authentication에 사용자 정보 담길 것
            Authentication authentication = authenticationManager.authenticate(authenticationToken);

            //PrincipalDetails를 세션에 담기 -> 등급 구분 없으면 session에 안 담아도 됨
            //authentication은 Object로 받아오기 때문에 다운캐스팅 필요
            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            log.info("----- 로그인 성공 : ", principalDetails.getMember().getEmail());

            return authentication;

        } catch (IOException e) {
            e.printStackTrace();
        }




        
        //4. JWT 토큰 만들어서 응답하기


        return null;
    }
}
