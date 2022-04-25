package com.review.gradle_java11_sb2512.services;

import com.review.gradle_java11_sb2512.entities.AccountEntity;
import com.review.gradle_java11_sb2512.mappers.AccountMapper;
import com.review.gradle_java11_sb2512.repositories.AccountRepo;
import com.review.gradle_java11_sb2512.wrappers.AccountWrap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class AccountService {

    final AccountRepo accountRepo;

    final AccountMapper accountMapper;

    public Flux<AccountEntity> get(){

        return accountRepo.findAll();
    }

    public Mono<AccountEntity> post(AccountWrap accountWrap){

        AccountEntity accountEntity = accountMapper.toEntity(accountWrap);

        return accountRepo.save(accountEntity);
    }

    public Mono<AccountEntity> put(Long id, AccountWrap accountWrap){

        AccountEntity accountEntity = accountMapper.toEntity(accountWrap);

        accountEntity.setId(id);

        return accountRepo.save(accountEntity);
    }

    public Mono<Void> delete(Long id){

        return accountRepo.deleteById(id);
    }

}
