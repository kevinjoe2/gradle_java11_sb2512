package com.review.gradle_java11_sb2512.repositories;

import com.review.gradle_java11_sb2512.entities.AccountEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepo extends R2dbcRepository<AccountEntity,Long> {
}
