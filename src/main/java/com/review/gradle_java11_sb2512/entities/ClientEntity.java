package com.review.gradle_java11_sb2512.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@Table(value = "clients")
@Getter
@Setter
public class ClientEntity extends PersonEntity implements Serializable {

    @Id
    private Long id;

    @NonNull
    private String clientNumber;

    @Version
    private Long version;

}
