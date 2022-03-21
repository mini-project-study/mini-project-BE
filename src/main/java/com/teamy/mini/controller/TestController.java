package com.teamy.mini.controller;

import com.teamy.mini.domain.Member;
import com.teamy.mini.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private MemberService memberService;

    public TestController(MemberService memberService){
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
    @GetMapping("test")
    public Member test(@RequestParam String email, @RequestParam String nickname, @RequestParam String password) {
        Member member = new Member(email, nickname, password);
        memberService.registerMember(member);
        return member;
    }

}
