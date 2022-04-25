package com.review.gradle_java11_sb2512.mappers;

import com.review.gradle_java11_sb2512.entities.ParameterEntity;
import com.review.gradle_java11_sb2512.wrappers.ParameterWrap;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ParameterMapper {

    ParameterEntity toEntity(ParameterWrap parameterWrap);
    
}
