package com.review.gradle_java11_sb2512.entities;

import com.review.gradle_java11_sb2512.utils.emuns.AccountTypeEnum;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table(value = "accounts")
@Getter
@Setter
@ToString
public class AccountEntity {

    @Id
    private Long id;

    private AccountTypeEnum accountType;

    private Long client_id;

}
