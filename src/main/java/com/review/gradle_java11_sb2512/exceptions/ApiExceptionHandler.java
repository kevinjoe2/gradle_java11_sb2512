package com.review.gradle_java11_sb2512.exceptions;

import com.review.gradle_java11_sb2512.wrappers.ErrorWrap;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = ConflictException.class)
    public ResponseEntity<ErrorWrap> runtimeExceptionHandler(ConflictException ex ){
        return new ResponseEntity<>(new ErrorWrap(ex.getCode(),ex.getMessage(),ex.getStatus()), ex.getStatus());
    }

}
