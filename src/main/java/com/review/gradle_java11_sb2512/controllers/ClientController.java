package com.review.gradle_java11_sb2512.controllers;

import com.review.gradle_java11_sb2512.entities.ClientEntity;
import com.review.gradle_java11_sb2512.services.ClientService;
import com.review.gradle_java11_sb2512.wrappers.ClientWrapper;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("clients")
@AllArgsConstructor
public class ClientController {

    final ClientService clientService;

    @GetMapping
    public Flux<ClientWrapper> get() {

        return clientService.get();
    }

    @GetMapping(value = "getPersonWrap", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<Flux<ClientEntity>> getPersonWrap() {

        return new ResponseEntity<>(clientService.getPersonWrap(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Mono<ClientEntity>> post(@RequestBody ClientWrapper clientWrapper) {

        return new ResponseEntity<>(clientService.post(clientWrapper), HttpStatus.OK);
    }

    @PostMapping(value = "postAll", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ClientWrapper> postAll(@RequestBody List<ClientWrapper> clientWrapperList) {

        return clientService.saveAllV7(clientWrapperList);
    }

    @PutMapping("{id}")
    public ResponseEntity<Mono<ClientEntity>> put(@PathVariable Long id, @RequestBody ClientWrapper clientWrap) {

        return new ResponseEntity<>(null,HttpStatus.CREATED);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Mono<Void>> delete(@PathVariable Long id) {

        return new ResponseEntity<>(clientService.delete(id), HttpStatus.GONE);
    }
}
