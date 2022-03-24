package com.teamy.mini.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    INVALID_INPUT_VALUE(400,"Invalid Input Value"),
    INQUIRY_NO_RESULT(400,"There is No inquiry result"),
    METHOD_NOT_ALLOWED(405,  "Method Not Allowed"),
    HANDLE_ACCESS_DENIED(403, "Access is Denied"),
    TOKEN_MALFORMED_JWT(400, "토큰 시그니쳐에 문제가 있음"),
    TOKEN_EXPIRED_JWT(400, "만료된 토큰"),
    TOKEN_UNSUPPORTED_JWT(400, "지원하지 않는 토큰"),
    TOKEN_ILLEGAL_JWT(400, "JWT 토큰이 잘못됨");

    private int statusCode;
    private final String message;


    ErrorCode(final int statusCode, final String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}