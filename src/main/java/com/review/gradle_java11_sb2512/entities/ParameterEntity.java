package com.review.gradle_java11_sb2512.entities;

import com.review.gradle_java11_sb2512.utils.emuns.ParameterCodeEnum;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "parameters")
@Getter
@Setter
public class ParameterEntity {

    @Id
    private Long id;

    private ParameterCodeEnum code;

    private String description;

    private int value;

    private String prefix;

    private String suffix;

    private Integer length;

}
