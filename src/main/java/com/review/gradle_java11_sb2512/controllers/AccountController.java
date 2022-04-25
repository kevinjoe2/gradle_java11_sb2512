package com.review.gradle_java11_sb2512.controllers;

import com.review.gradle_java11_sb2512.entities.AccountEntity;
import com.review.gradle_java11_sb2512.services.AccountService;
import com.review.gradle_java11_sb2512.wrappers.AccountWrap;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("account")
@AllArgsConstructor
public class AccountController {

    final AccountService accountService;

    @GetMapping
    public ResponseEntity<Flux<AccountEntity>> get() {
        return new ResponseEntity<>(accountService.get(),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mono<AccountEntity>> post(@RequestBody AccountWrap accountWrap) {
        return new ResponseEntity<>(accountService.post(accountWrap),HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Mono<AccountEntity>> put(@PathVariable Long id, @RequestBody AccountWrap accountWrap) {
        return new ResponseEntity<>(accountService.put(id, accountWrap),HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Long id) {
        return new ResponseEntity<>(accountService.delete(id), HttpStatus.GONE);
    }

}
