package com.review.gradle_java11_sb2512.services;

import com.review.gradle_java11_sb2512.entities.ClientEntity;
import com.review.gradle_java11_sb2512.entities.ParameterEntity;
import com.review.gradle_java11_sb2512.exceptions.ConflictException;
import com.review.gradle_java11_sb2512.mappers.ClientMapper;
import com.review.gradle_java11_sb2512.repositories.ClientRepo;
import com.review.gradle_java11_sb2512.repositories.ParameterRepo;
import com.review.gradle_java11_sb2512.utils.emuns.ParameterCodeEnum;
import com.review.gradle_java11_sb2512.utils.functions.ParameterFunction;
import com.review.gradle_java11_sb2512.wrappers.ClientWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.r2dbc.connection.R2dbcTransactionManager;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransactionManager;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log
@Service
@AllArgsConstructor
public class ClientService {

    final ClientRepo clientRepo;

    final ParameterRepo parameterRepo;

    final ClientMapper clientMapper;

    public Flux<ClientWrapper> get() {
        log.info("Hola");
        return clientRepo.findAll().map(clientMapper::toWrapper);
    }

    public Flux<ClientEntity> getPersonWrap() {

        return clientRepo.findAll();
    }

    public Mono<ClientEntity> post(ClientWrapper clientWrap) {

        return assertClientNotExists(clientWrap.getIdentification())

                .then(nextClientNumber(clientMapper.toEntity(clientWrap)))

                .flatMap(clientRepo::save);
    }

    public Flux<ClientWrapper> postAll(List<ClientWrapper> clientWrapperList) {

        return Flux.fromStream(clientWrapperList.stream())

                .switchIfEmpty(Mono.error(new ConflictException("400", "Client is empty", HttpStatus.CONFLICT)))

                .flatMap(this::save);
    }

    @Transactional
    Mono<ClientWrapper> save(ClientWrapper clientWrap) {

        return assertClientNotExists(clientWrap.getIdentification())

                .then(nextClientNumber(clientMapper.toEntity(clientWrap)))

                .flatMap(clientRepo::save)

                .map(clientMapper::toWrapper);
    }

    private Mono<Void> assertClientNotExists(String identification) {

        return clientRepo.findByIdentification(identification)

                .flatMap(clientEntity -> Mono.error(
                        new ConflictException("400", "Client already exists: " + identification, HttpStatus.CONFLICT)
                ));
    }

    private Mono<ClientEntity> nextClientNumber(ClientEntity clientEntity) {

        return parameterRepo.findByCode(ParameterCodeEnum.CLIENT_NUMBER)

                .switchIfEmpty(Mono.error(new ConflictException("400", "Parameter " + ParameterCodeEnum.CLIENT_NUMBER.toString() + " not found", HttpStatus.CONFLICT)))

                .flatMap(parameterEntity -> {
                    parameterEntity.setValue(parameterEntity.getValue() + 1);
                    return parameterRepo.save(parameterEntity);
                })

                .map(m -> {
                    clientEntity.setClientNumber(m.getPrefix() + m.getValue() + m.getSuffix());
                    return clientEntity;
                });
    }

    private Mono<String> nextClientNumber() {

        return parameterRepo.findByCode(ParameterCodeEnum.CLIENT_NUMBER)

                .switchIfEmpty(Mono.error(new ConflictException("400", "Parameter " + ParameterCodeEnum.CLIENT_NUMBER.toString() + " not found", HttpStatus.CONFLICT)))

                .flatMap(parameterEntity -> {
                    parameterEntity.setValue(parameterEntity.getValue() + 1);
                    return parameterRepo.save(parameterEntity);
                })

                .map(m -> m.getPrefix() + m.getValue() + m.getSuffix());
    }

//    public Flux<ClientWrapper> saveAll(List<ClientWrapper> clientWrapperFlux) {
//        log.info("1");
//        return Flux.fromStream(clientWrapperFlux.stream())
//                .flatMap(clientWrapperOri ->
//
//                        clientRepo.findByIdentification(clientWrapperOri.getIdentification())
//                                .switchIfEmpty(Mono.error(new ConflictException("400", "CLIENT ALREADY EXIST", HttpStatus.CONFLICT)))
//                                .flatMap(clientNotExist ->
//
//                                        parameterRepo.findByCode(ParameterCodeEnum.CLIENT_NUMBER)
//                                                .switchIfEmpty(Mono.error(new ConflictException("400", "PARAMETER NOT FOUND", HttpStatus.CONFLICT)))
//                                                .flatMap(parameter -> {
//
//                                                    String clientNumber = parameter.getPrefix() + parameter.getValue() + parameter.getSuffix();
//                                                    parameter.setValue(parameter.getValue() + 1);
//                                                    return parameterRepo.save(parameter)
//                                                            .flatMap(m -> {
//
//                                                                ClientEntity clientEntity = clientMapper.toEntity(clientWrapperOri);
//                                                                clientEntity.setClientNumber(clientNumber);
//                                                                return clientRepo.save(clientEntity).map(clientMapper::toWrapper);
//                                                            });
//                                                })
//                                )
//                );
//    }

//    @Transactional
//    public Mono<ClientWrapper> saveAllV2(List<ClientWrapper> clientWrapperFlux) {
//        log.info("2.1");
//
//        return Mono.just(clientWrapperFlux)
//                .map(clientWrapperOri ->
//
//                        clientWrapperOri
//                                .stream()
//                                .map(clientWrapperOri2 -> parameterRepo.findByCode(ParameterCodeEnum.CLIENT_NUMBER)
//                                        .switchIfEmpty(Mono.error(new ConflictException("400", "PARAMETER NOT FOUND", HttpStatus.CONFLICT)))
//                                        .flatMap(parameter -> {
//
//                                            List<ClientWrapper> clientWrappers = new ArrayList();
//
//                                            log.info("2.3");
//                                            parameter.setValue(parameter.getValue() + 1);
//                                            return parameterRepo.save(parameter)
//                                                    .flatMap(parameterEntity -> {
//
//                                                        log.info("2.4");
//
//                                                        ClientEntity clientEntity = clientMapper.toEntity(clientWrapperOri2);
//                                                        clientEntity.setClientNumber(parameterEntity.getPrefix() + parameterEntity.getValue() + parameterEntity.getSuffix());
//                                                        clientRepo.save(clientEntity).map(clientWrappers::add);
//
//                                                        return clientWrappers;
//                                                    });
//                                        })).flatMap(f -> f.flatMap(f1 ->));
//                );
//    }

    @Transactional
    public Flux<ClientWrapper> saveAllV3(List<ClientWrapper> clientWrapperList) {

        return Flux.fromStream(clientWrapperList.stream())
                .flatMap(p1 ->

                        assertClientExist(p1)
                                .flatMap(p2 ->

                                        {

                                            if (!p2) {

                                                return parameterRepo.findByCode(ParameterCodeEnum.CLIENT_NUMBER)
                                                        .doOnNext(a -> {
                                                            log.info("doOnNext");
                                                        })
                                                        .doOnSuccess(rp -> {
                                                            log.info("doOnSuccess");
                                                        })
                                                        .doOnTerminate(() -> log.info("doOnTerminate"))
                                                        .doFinally(a -> log.info("doFinally"))
                                                        .flatMap(p5 -> {

                                                            log.info("flatMap");
                                                            log.info(String.valueOf(p5.getValue()));
                                                            p5.setValue(p5.getValue()+1);
                                                            return parameterRepo.save(p5)
                                                                    .doOnSuccess(a -> log.info("doOnSuccess Par"))
                                                                    .flatMap(p7 -> clientRepo
                                                                            .save(clientMapper.toEntity(p1))
                                                                            .doOnSuccess(a -> log.info("doOnSuccess Cli"))
                                                                            .map(clientMapper::toWrapper));
                                                        });

                                            } else {
                                                return clientRepo.findByIdentification(p1.getIdentification())
                                                        .flatMap(p3 -> {

                                                            p3.setLastname(p3.getLastname() + ":" + p3.getVersion());
                                                            return clientRepo.save(p3)
                                                                    .map(clientMapper::toWrapper);
                                                        });
                                            }
                                        }
                                )
                );
    }

    public Flux<ClientWrapper> saveAllV5(List<ClientWrapper> clientWrapperList){

        return parameterRepo
                .findByCode(ParameterCodeEnum.CLIENT_NUMBER)
                .doFinally(e -> log.info("1 findByCode(ParameterCodeEnum.CLIENT_NUMBER)"))
                .flatMapMany(parIni ->

                        Flux.fromStream(clientWrapperList.stream())
                                .doFinally(e -> log.info("2 Flux.fromStream(clientWrapperList.stream())"))
                                .flatMap(cliWra -> {

                                    parIni.setValue(parIni.getValue() + 1);
                                    return parameterRepo.save(parIni)
                                            .doFinally(e -> log.info("3 parameterRepo.save(parIni)"))
                                            .flatMap(parNue -> {

                                                return parameterRepo.findByCode(ParameterCodeEnum.CLIENT_NUMBER)
                                                        .doOnSuccess(e -> log.info("4 parameterRepo.findByCode(ParameterCodeEnum.CLIENT_NUMBER)" + e.getValue()))
                                                        .flatMap(parFin -> {

                                                            String clientNumber = parNue.getPrefix() + parNue.getValue() + parNue.getSuffix();
                                                            log.info("4.2 " + clientNumber);

                                                            ClientEntity cliEnt = clientMapper.toEntity(cliWra);
                                                            cliEnt.setClientNumber(clientNumber);

                                                            return clientRepo.save(cliEnt)
                                                                    .doFinally(e -> log.info("5 clientRepo.save(cliEnt)"))
                                                                    .map(clientMapper::toWrapper);
                                                        });
                                            });
                                })
                );
    }

    public Flux<ClientWrapper> saveAllV6(List<ClientWrapper> clientWrapperList){

        return Flux.fromStream(clientWrapperList.stream())
                .flatMap(cliWra -> {

                    return parameterRepo.findByCode(ParameterCodeEnum.CLIENT_NUMBER)
                            .flatMap(par -> {

                                log.info("1. PARAMETRO GENERADO: " + par.getPrefix()+par.getValue()+par.getSuffix());
                                ClientEntity cli = clientMapper.toEntity(cliWra);
                                cli.setClientNumber(par.getPrefix()+par.getValue()+par.getSuffix());
                                return clientRepo.save(cli)
                                        .flatMap(cliEnt -> {

                                            log.info("2. CODIGO GUARDADO: " + cliEnt.getClientNumber());
                                            log.info("2. CODIGO GUARDADO: " + cliEnt.getIdentification());
                                            par.setValue(par.getValue()+1);
                                            return parameterRepo.save(par)
                                                    .map(parSav -> {

                                                        log.info("3. PARAMETRO GUARDADO: " + parSav.getPrefix()+parSav.getValue()+parSav.getSuffix());
                                                        return clientMapper.toWrapper(cliEnt);
                                                    });
                                        });
                            });
                });
    }

    public Flux<ClientWrapper> saveAllV7(List<ClientWrapper> clientWrapperList){

        return Flux.fromIterable(clientWrapperList)
                .flatMap(cliWra -> {

                    return parameterRepo.findByCode(ParameterCodeEnum.CLIENT_NUMBER)
                            .flatMap(par -> {

                                log.info("1. PARAMETRO GENERADO: " + par.getPrefix()+par.getValue()+par.getSuffix());
                                ClientEntity cli = clientMapper.toEntity(cliWra);
                                cli.setClientNumber(par.getPrefix()+par.getValue()+par.getSuffix());
                                return clientRepo.save(cli)
                                        .flatMap(cliEnt -> {

                                            log.info("2.1. CODIGO GUARDADO: " + cliEnt.getClientNumber());
                                            log.info("2.2. CODIGO GUARDADO: " + cliEnt.getIdentification());
                                            par.setValue(par.getValue()+1);
                                            return parameterRepo.save(par)
                                                    .map(parSav -> {

                                                        log.info("3. PARAMETRO GUARDADO: " + parSav.getPrefix()+parSav.getValue()+parSav.getSuffix());
                                                        return clientMapper.toWrapper(cliEnt);
                                                    });
                                        });
                            });
                }, 1);
    }

    public Mono<Boolean> assertClientExist(ClientWrapper clientWrapper) {

        return clientRepo.findByIdentification(clientWrapper.getIdentification())
                .flatMap(f -> Mono.just(true))
                .defaultIfEmpty(false);
    }

    public Mono<ClientWrapper> saveClient(String clientNumber, ClientEntity clientEntity) {

        log.info(clientNumber);
        clientEntity.setClientNumber(clientNumber);
        return clientRepo.save(clientEntity).map(clientMapper::toWrapper);
    }

    public Mono<ClientEntity> put(Long id, ClientWrapper clientWrap) {

        ClientEntity ClientEntity = clientMapper.toEntity(clientWrap);

        ClientEntity.setId(id);

        return clientRepo.save(ClientEntity);
    }

    public Mono<Void> delete(Long id) {

        return clientRepo.deleteById(id);
    }

}
