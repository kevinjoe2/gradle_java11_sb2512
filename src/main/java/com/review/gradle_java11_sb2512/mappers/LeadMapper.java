package com.review.gradle_java11_sb2512.mappers;

import com.review.gradle_java11_sb2512.entities.LeadEntity;
import com.review.gradle_java11_sb2512.wrappers.LeadWrapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LeadMapper {

    LeadEntity toEntity(LeadWrapper clientMapper);

}
