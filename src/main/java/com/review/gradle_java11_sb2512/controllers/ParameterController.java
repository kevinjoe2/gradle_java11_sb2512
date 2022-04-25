package com.review.gradle_java11_sb2512.controllers;

import com.review.gradle_java11_sb2512.entities.ParameterEntity;
import com.review.gradle_java11_sb2512.services.ParameterService;
import com.review.gradle_java11_sb2512.wrappers.ParameterWrap;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("parameter")
@AllArgsConstructor
public class ParameterController {

    ParameterService parameterService;

    @GetMapping
    public ResponseEntity<Flux<ParameterEntity>> get() {

        return new ResponseEntity<>(parameterService.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mono<ParameterEntity>> post(@RequestBody ParameterWrap parameterWrap) {

        return new ResponseEntity<>(parameterService.post(parameterWrap),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Mono<ParameterEntity>> put(@PathVariable Long id, @RequestBody ParameterWrap parameterWrap) {

        return new ResponseEntity<>(parameterService.put(id, parameterWrap),HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Long id) {

        return new ResponseEntity<>(parameterService.delete(id), HttpStatus.GONE);
    }
    
}
