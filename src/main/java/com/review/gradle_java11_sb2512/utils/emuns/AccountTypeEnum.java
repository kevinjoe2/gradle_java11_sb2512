package com.review.gradle_java11_sb2512.utils.emuns;

import lombok.Getter;

@Getter
public enum AccountTypeEnum {

    A("SAVING"),C("CURRENT");

    private final String typeAccount;

    AccountTypeEnum(String _typeAccount) {
        this.typeAccount = _typeAccount;
    }
}
