package com.review.gradle_java11_sb2512.wrappers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
public class ErrorWrap {

    String code, message;
    HttpStatus status;

}
