package com.review.gradle_java11_sb2512.services;

import com.review.gradle_java11_sb2512.entities.LeadEntity;
import com.review.gradle_java11_sb2512.mappers.LeadMapper;
import com.review.gradle_java11_sb2512.repositories.LeadRepo;
import com.review.gradle_java11_sb2512.wrappers.LeadWrapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class LeadService {

    private LeadRepo leadRepo;

    final LeadMapper leadMapper;

    public Flux<LeadEntity> get() {

        return leadRepo.findAll();
    }

    public Mono<LeadEntity> post(LeadWrapper leadWrapper){

        LeadEntity LeadEntity = leadMapper.toEntity(leadWrapper);

        return leadRepo.save(LeadEntity);
    }

    public Mono<LeadEntity> put(Long id, LeadWrapper leadWrapper){

        LeadEntity LeadEntity = leadMapper.toEntity(leadWrapper);

        LeadEntity.setId(id);

        return leadRepo.save(LeadEntity);
    }

    public Mono<Void> delete(Long id){

        return leadRepo.deleteById(id);
    }

}
