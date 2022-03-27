package com.teamy.mini.security;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class TestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if(req.getMethod().equals("POST")) {
            System.out.println("POST 요청됨됨");
            String headerAuth = req.getHeader("Authorization");
            System.out.println("headerAuth : " + headerAuth);

            //cos가 토큰역할
            //id, pw가 정상적으로 들어와서 로그인 완료 시, 토큰을 만들어주면
            //요청 시마다 header에 Authorization에 value 값으로 토큰을 가져올텐데
            //해당 토큰이 내 서버가 만든 토큰이 맞는지 (RSA, HS256을 사용해) 검증하면 됨
            if(headerAuth.equals("cos")) {
                filterChain.doFilter(req, res);
            } else {
                PrintWriter out = res.getWriter();
                out.println("인증 안됨");
                System.out.println("PrintWriter랑 그냥이랑 머가 다름");
            }
       }
    }
}

