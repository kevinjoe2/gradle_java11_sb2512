package com.review.gradle_java11_sb2512.controllers;

import com.review.gradle_java11_sb2512.entities.MovementEntity;
import com.review.gradle_java11_sb2512.services.MovementService;
import com.review.gradle_java11_sb2512.wrappers.MovementWrap;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("movement")
@AllArgsConstructor
public class MovementController {

    final MovementService movementService;

    @GetMapping
    public ResponseEntity<Flux<MovementEntity>> get() {

        return new ResponseEntity<>(movementService.get(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mono<MovementEntity>> post(@RequestBody MovementWrap movementWrap) {

        return new ResponseEntity<>(movementService.post(movementWrap),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Mono<MovementEntity>> put(@PathVariable Long id, @RequestBody MovementWrap movementWrap) {

        return new ResponseEntity<>(movementService.put(id, movementWrap),HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Long id) {

        return new ResponseEntity<>(movementService.delete(id), HttpStatus.GONE);
    }

}
