package com.review.gradle_java11_sb2512.repositories;

import com.review.gradle_java11_sb2512.entities.ParameterEntity;
import com.review.gradle_java11_sb2512.utils.emuns.ParameterCodeEnum;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Mono;

public interface ParameterRepo extends R2dbcRepository<ParameterEntity, Long> {

    Mono<ParameterEntity> findByCode(ParameterCodeEnum code);

}
