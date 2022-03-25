package com.teamy.mini.jwt;

import com.teamy.mini.error.ErrorCode;
import com.teamy.mini.repository.MemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//우리가 JWT를 사용하는 가장 큰 장점은, DB에 접근하지 않고 인메모상의 키값을 이용해 사용자의 권한을 체크하기 위함입니다.
// 때문에 tokenProvider를 만들어 토큰 검증을 하고, 이를 "Filter"에 등록합니다.
//하지만 ControllerAdvice는 Filter, Interceptor 단에서 발생하는 Exception은 처리를 해주지 못합니다.
//https://velog.io/@dltkdgns3435/%EC%8A%A4%ED%94%84%EB%A7%81%EC%8B%9C%ED%81%90%EB%A6%AC%ED%8B%B0-JWT-%EC%98%88%EC%99%B8%EC%B2%98%EB%A6%AC

@Slf4j
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private MemberRepository memberRepository;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 유효한 자격증명 제공x -> 401
        log.info("유효한 자격증명 제공x -> 401");


        log.info("authException : {}", authException.getLocalizedMessage());
        log.info("authException : {}", authException.getMessage());

        if(authException instanceof BadCredentialsException) {
            log.info("bad");
            request.setAttribute("Exception", ErrorCode.LOGIN_INFO_NOT_FOUND);
        } /*else if(authException instanceof UsernameNotFoundException) {
            log.info("usernamenot");
            request.setAttribute("Exception", ErrorCode.LOGIN_INFO_NOT_FOUND);
        }*/

            //response.sendRedirect("/loginFail");

        ErrorCode errorCode = (ErrorCode) request.getAttribute("Exception");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseJson = new JSONObject();
        responseJson.put("success", false);
        responseJson.put("message", errorCode.getMessage());
        responseJson.put("error", errorCode.getMessage());
        responseJson.put("data", null);

        response.setStatus(errorCode.getStatusCode());
        response.getWriter().print(responseJson);
    }
//        switch(errorCode){
//            case TOKEN_MALFORMED_JWT:
//                request.
//                break;
//            case TOKEN_EXPIRED_JWT:
//                break;
//            case TOKEN_ILLEGAL_JWT:
//                break;
//        }


}
