package com.review.gradle_java11_sb2512.mappers;

import com.review.gradle_java11_sb2512.entities.AccountEntity;
import com.review.gradle_java11_sb2512.wrappers.AccountWrap;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {


    AccountEntity toEntity(AccountWrap accountWrap);

}
