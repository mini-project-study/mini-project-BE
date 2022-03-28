package com.teamy.mini.error;

import lombok.*;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ErrorResponse {
    private String message;
    private int statusCode;
    private List<FieldError> errors = new ArrayList<>();
    private StackTraceElement[] stackTrace;

    public ErrorResponse(ErrorCode code) {
        this.message = code.getMessage();
        this.statusCode = code.getStatusCode();
    }
    public ErrorResponse(ErrorCode code, StackTraceElement[] stackTrace) {
        this.message = code.getMessage();
        this.statusCode = code.getStatusCode();
        this.stackTrace = stackTrace;
    }


    public ErrorResponse(ErrorCode errorCode, BindingResult bindingResult) {
        this.statusCode = errorCode.getStatusCode();

        var errors = bindingResult.getFieldErrors();
        for(var error : errors) {
            String field = error.getField();
            String reason = error.getDefaultMessage();
            this.errors.add(new FieldError(field, reason));
        }
    }


    public static ErrorResponse of(ErrorCode errorCode, BindingResult bindingResult) {
        return new ErrorResponse(errorCode, bindingResult);
    }


    public static ErrorResponse of(ErrorCode errorCode) {
        return new ErrorResponse(errorCode);
    }


    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class FieldError {
        private String field;
        private String reason;
    }
}