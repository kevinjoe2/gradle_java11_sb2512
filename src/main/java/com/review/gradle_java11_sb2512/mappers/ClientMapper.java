package com.review.gradle_java11_sb2512.mappers;

import com.review.gradle_java11_sb2512.entities.ClientEntity;
import com.review.gradle_java11_sb2512.wrappers.ClientWrapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ClientMapper {

    ClientEntity toEntity(ClientWrapper clientMapper);

    ClientWrapper toWrapper(ClientEntity clientEntity);

}
