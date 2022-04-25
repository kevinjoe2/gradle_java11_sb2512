package com.review.gradle_java11_sb2512.wrappers;

import lombok.*;

import java.time.LocalDate;

@Data
public class ClientWrapper {

    private String identification;
    private String name;
    private String lastname;
    private String phone;
    private String homeAddress;
    private String workAddress;
    private LocalDate dateOfBirth;

}
