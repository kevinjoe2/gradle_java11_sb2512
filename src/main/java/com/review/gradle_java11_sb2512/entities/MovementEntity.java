package com.review.gradle_java11_sb2512.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(value = "movements")
@Getter
@Setter
public class MovementEntity {

    @Id
    private Long id;

    private LocalDateTime transactionDate;

    private Long accountOriginId;

    private Long accountDestinationId;

}
