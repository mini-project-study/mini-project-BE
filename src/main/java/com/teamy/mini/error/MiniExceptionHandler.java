package com.teamy.mini.error;


import com.teamy.mini.domain.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class MiniExceptionHandler {

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<ResponseMessage> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        return new ResponseEntity<ResponseMessage>(new ResponseMessage(false, ErrorCode.SQL_ERROR.getMessage(), ErrorCode.SQL_ERROR.getMessage(), null), HttpStatus.valueOf(ErrorCode.SQL_ERROR.getStatusCode()));
    }



}
