package com.teamy.mini.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

@Setter
@Getter
public class ResponseMessage {

    boolean success;
    String message;
    String error;
    JwtToken data;

    public ResponseMessage(boolean success, String message, String error, JwtToken data) {
        this.success = success;
        this.message = message;
        this.error = error;
        this.data = data;
    }
}
