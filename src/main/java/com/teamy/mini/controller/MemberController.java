package com.teamy.mini.controller;

import com.teamy.mini.domain.LoginInfo;
import com.teamy.mini.domain.Member;
import com.teamy.mini.domain.ResponseMessage;
import com.teamy.mini.jwt.JwtFilter;
import com.teamy.mini.jwt.JwtAuthenticationProvider;
import com.teamy.mini.jwt.MemberAccount;
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

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
public class MemberController {

    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private MemberService memberService;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public MemberController(BCryptPasswordEncoder bCryptPasswordEncoder, MemberService memberService, JwtAuthenticationProvider jwtAuthenticationProvider, AuthenticationManagerBuilder authenticationManagerBuilder){
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtAuthenticationProvider = jwtAuthenticationProvider;
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
    public ResponseEntity<String> test(@RequestParam String email, @RequestParam String nickname, @RequestParam String password) {
        String encodedPassword = bCryptPasswordEncoder.encode(password);
        Member member = new Member(email, nickname, encodedPassword);
        memberService.registerMember(member);
        return new ResponseEntity<>("success", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage> authorize(@RequestBody LoginInfo loginInfo) {

//        try{
//            Member member = memberService.login(loginInfo);
//        } catch (InquiryException e2) {
//            return new ResponseEntity<>(
//                    new ResponseMessage(false, "아이디 혹은 비밀번호를 확인해주세요.", "유저 정보 조회 결과 없음", null)
//                    , HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginInfo.getEmail(), loginInfo.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        // token에 문제 있거나 아이디/비번 틀려서 로그인에 문제 있으면 여기로 넘어오지 않고 바로 jwtAuthenticationEntryPoint 로 넘어감
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("인증된 사용자 email " + SecurityContextHolder.getContext().getAuthentication().getName());

        String nickname = ((MemberAccount) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getMember().getNickname();
        String jwt = jwtAuthenticationProvider.createAccessToken(authentication);
        //log.info("jwt : {} ", jwt);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(JwtFilter.AUTHORIZTION_HADER, "Bearer " + jwt);

        Map<String, Object> data = new HashMap<>();
        data.put("nickname", nickname);
        data.put("accessToken", jwt);
        data.put("refreshToken", jwt);
        return new ResponseEntity<>(new ResponseMessage(true, "로그인 성공", "", data), httpHeaders, HttpStatus.OK);

    }

}
