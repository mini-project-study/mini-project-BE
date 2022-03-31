package com.teamy.mini.controller;

import com.teamy.mini.domain.LoginInfo;
import com.teamy.mini.domain.Member;
import com.teamy.mini.domain.ResponseMessage;
import com.teamy.mini.jwt.JwtFilter;
import com.teamy.mini.jwt.JwtAuthenticationProvider;
import com.teamy.mini.security.MemberAccount;
import com.teamy.mini.service.MemberService;
import com.teamy.mini.service.RedisTestService;
import io.jsonwebtoken.Jwts;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MemberController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberService memberService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisTestService redisTestService;


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
    @PostMapping("users")
    public ResponseEntity<String> memberRegister(@RequestParam String email, @RequestParam String nickname, @RequestParam String password) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        Member member = new Member(email, nickname, encodedPassword);
        memberService.registerMember(member);

        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("auth/login")
    public ResponseEntity<ResponseMessage> authorize(@RequestBody LoginInfo loginInfo) {

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginInfo.getEmail(), loginInfo.getPassword());
        log.info("엉엉 " + authenticationToken.getName());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // token에 문제 있거나 아이디/비번 틀려서 로그인에 문제 있으면 여기로 넘어오지 않고 바로 jwtAuthenticationEntryPoint 로 넘어감
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("인증된 사용자 email " + SecurityContextHolder.getContext().getAuthentication().getName());

        String nickname = ((MemberAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember().getNickname();
        String id = String.valueOf(((MemberAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember().getId());
        String jwt = jwtAuthenticationProvider.createAccessToken(authentication);
        //log.info("jwt : {} ", jwt);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZTION_HADER, "Bearer " + jwt);

        Map<String, Object> data = new HashMap<>();
        data.put("nickname", nickname);
        data.put("id", id);
        data.put("accessToken", jwt);
        data.put("refreshToken", jwt);
        return new ResponseEntity<>(new ResponseMessage(true, "로그인 성공", "", data), httpHeaders, HttpStatus.OK);

    }

    @PostMapping("auth/logout")
    public ResponseEntity<ResponseMessage> logout(@RequestHeader Map<String, Object> requestHeader){

        //log.info("token : " + headers.get("token").toString());
        String token = (String) requestHeader.get("authorization");

        redisTestService.setLogoutToken(token, jwtAuthenticationProvider.getTokenExpire(token));
        return new ResponseEntity<>(new ResponseMessage(true, "로그아웃 성공", "", null), HttpStatus.OK);
    }

    


}
