package com.teamy.mini.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Setter
@Getter
public class ResponseMessage {

    boolean success;
    String message;
    String error;
    Map<String, Object> data;

    public ResponseMessage(boolean success, String message, String error, Map<String, Object> data) {
        this.success = success;
        this.message = message;
        this.error = error;
        this.data = data;
    }

}
