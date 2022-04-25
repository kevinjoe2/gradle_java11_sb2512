package com.review.gradle_java11_sb2512.utils.emuns;

public enum PersonType {

    CLI("CLIENT"), LEA("LEAFED");

    final String desc;

    PersonType(String _desc) {
        this.desc = _desc;
    }

}
