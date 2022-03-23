package com.teamy.mini.controller;

import com.teamy.mini.domain.JwtToken;
import com.teamy.mini.domain.LoginInfo;
import com.teamy.mini.domain.Member;
import com.teamy.mini.jwt.JwtFilter;
import com.teamy.mini.jwt.JwtTokenProvider;
import com.teamy.mini.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class TestController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public TestController(BCryptPasswordEncoder bCryptPasswordEncoder, MemberService memberService, JwtTokenProvider jwtTokenProvider, AuthenticationManagerBuilder authenticationManagerBuilder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.memberService = memberService;
    }

    @Operation(summary = "test hello", description = "hello api example")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST !!"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND !!"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR !!")
    })
    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @Operation(summary = "Swagger Test", description = "Testing~~!")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공이다"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청"),
            @ApiResponse(responseCode = "404", description = "페이지 없다"),
            @ApiResponse(responseCode = "500", description = "서버가 뭔가 잘못했다")
    })
    @PostMapping("join")
    public Member test(@RequestParam String email, @RequestParam String nickname, @RequestParam String password) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        Member member = new Member(email, nickname, encodedPassword);
        memberService.registerMember(member);
        return member;
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JwtToken> authorize(@RequestBody LoginInfo loginInfo) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginInfo.getEmail(), loginInfo.getPassword());

        log.info("/authenticate -> 입력된 email : {} ", authenticationToken.getName());
        //findByEmail은 안 써? 뭘로 인증하는 거임

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("인증된 사용자 email " + SecurityContextHolder.getContext().getAuthentication().getName());

        String jwt = jwtTokenProvider.createAccessToken(authentication);
        log.info("jwt : {} ", jwt);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZTION_HADER, "Bearer " + jwt);

        return new ResponseEntity<>(new JwtToken(jwt, jwt), httpHeaders, HttpStatus.OK);
    }

}
