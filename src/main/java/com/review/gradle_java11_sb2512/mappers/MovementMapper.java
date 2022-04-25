package com.review.gradle_java11_sb2512.mappers;

import com.review.gradle_java11_sb2512.entities.MovementEntity;
import com.review.gradle_java11_sb2512.wrappers.MovementWrap;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MovementMapper {

    MovementEntity toEntity(MovementWrap movementWrap);
    
}
