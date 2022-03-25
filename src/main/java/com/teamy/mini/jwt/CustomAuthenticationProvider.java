//package com.teamy.mini.jwt;
//
//import com.teamy.mini.domain.Member;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@Slf4j
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//    @Autowired
//    private MemberDetailsService memberDetailsService;
//
//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//        String email = authentication.getName();
//        String password = (String) authentication.getCredentials();
//
//        log.info("여기 언제 옴?");
//        Member member = (Member) memberDetailsService.loadUserByUsername(email);
//
//        //비밀번호 검증
//        if(!bCryptPasswordEncoder.matches(password, member.getPassword())) {
//            throw new BadCredentialsException("비밀번호 불일치");
//        }
//
//        return authentication;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return false;
//    }
//}
