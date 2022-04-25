package com.review.gradle_java11_sb2512.repositories;

import com.review.gradle_java11_sb2512.entities.LeadEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeadRepo extends R2dbcRepository<LeadEntity,Long> {
}
