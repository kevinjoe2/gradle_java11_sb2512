package com.review.gradle_java11_sb2512.controllers;

import com.review.gradle_java11_sb2512.entities.LeadEntity;
import com.review.gradle_java11_sb2512.services.LeadService;
import com.review.gradle_java11_sb2512.wrappers.LeadWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("leaflet")
@AllArgsConstructor
public class LeadController {

    LeadService leadService;

    @GetMapping
    public ResponseEntity<Flux<LeadEntity>> get() {

        return new ResponseEntity<>(leadService.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mono<LeadEntity>> post(@RequestBody LeadWrapper leadWrapper) {

        return new ResponseEntity<>(leadService.post(leadWrapper),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Mono<LeadEntity>> put(@PathVariable Long id, @RequestBody LeadWrapper leadWrapper) {

        return new ResponseEntity<>(leadService.put(id, leadWrapper),HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Long id) {

        return new ResponseEntity<>(leadService.delete(id), HttpStatus.GONE);
    }

}
