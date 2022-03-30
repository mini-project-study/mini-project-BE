package com.teamy.mini.error;


import com.teamy.mini.domain.ResponseMessage;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.PropertyValueException;
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

//    @ExceptionHandler(IllegalArgumentException.class)
//    public ResponseEntity<ResponseMessage> handleIllegalArgumentException(IllegalArgumentException e) {
//        return new ResponseEntity<>(new ResponseMessage(false, ErrorCode.INVALID_INPUT_VALUE.getMessage(), ErrorCode.INVALID_INPUT_VALUE.getMessage(), null), HttpStatus.valueOf(ErrorCode.INVALID_INPUT_VALUE.getStatusCode()));
//    }
//
//    @ExceptionHandler(NullPointerException.class)
//    public ResponseEntity<ResponseMessage> NullPointerException(NullPointerException e) {
//        return new ResponseEntity<>(new ResponseMessage(false, ErrorCode.INVALID_INPUT_VALUE.getMessage(), ErrorCode.INVALID_INPUT_VALUE.getMessage(), null), HttpStatus.valueOf(ErrorCode.INVALID_INPUT_VALUE.getStatusCode()));
//    }

    @ExceptionHandler(PropertyValueException.class)
    public ResponseEntity<ResponseMessage> NullPointerException(PropertyValueException e) {
        return new ResponseEntity<>(new ResponseMessage(false, ErrorCode.PROPERTY_NULL.getMessage(), ErrorCode.PROPERTY_NULL.getMessage(), null), HttpStatus.valueOf(ErrorCode.PROPERTY_NULL.getStatusCode()));
    }



}
