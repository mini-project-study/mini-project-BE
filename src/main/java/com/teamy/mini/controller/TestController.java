package com.teamy.mini.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

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
    public String test(@RequestParam int t) { return "test"; }

}
