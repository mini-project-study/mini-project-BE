package com.teamy.mini.security;

import javax.servlet.*;
import java.io.IOException;

public class TestFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("testFilter");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}

