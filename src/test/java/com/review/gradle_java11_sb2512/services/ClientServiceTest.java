package com.review.gradle_java11_sb2512.services;

import com.review.gradle_java11_sb2512.entities.ClientEntity;
import com.review.gradle_java11_sb2512.entities.ParameterEntity;
import com.review.gradle_java11_sb2512.mappers.ClientMapper;
import com.review.gradle_java11_sb2512.repositories.ClientRepo;
import com.review.gradle_java11_sb2512.repositories.ParameterRepo;
import com.review.gradle_java11_sb2512.utils.emuns.ParameterCodeEnum;
import com.review.gradle_java11_sb2512.wrappers.ClientWrapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = ClientServiceTest.class)
class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepo clientRepo;

    @Mock
    private ParameterRepo parameterRepo;

    @Mock
    private ClientMapper clientMapper;

    @BeforeEach
    void setUp() {

        ParameterEntity parameterEntity = new ParameterEntity();
        parameterEntity.setId(1L);
        parameterEntity.setCode(ParameterCodeEnum.CLIENT_NUMBER);
        parameterEntity.setDescription("Generacion del codigo del clientedescription");
        parameterEntity.setLength(10);
        parameterEntity.setPrefix("CLI");
        parameterEntity.setSuffix("EC");
        parameterEntity.setValue(1);

        Mockito.when(parameterRepo.findByCode(ParameterCodeEnum.CLIENT_NUMBER))
                .thenReturn(Mono.just(parameterEntity));

        Mockito.when(parameterRepo.save(Mockito.any(ParameterEntity.class)))
                .thenReturn(Mono.just(parameterEntity));

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setIdentification("0401859376");
        clientEntity.setName("Kevin 1");
        clientEntity.setLastname("Chamorro 1");
        clientEntity.setPhone("0996427491");
        clientEntity.setHomeAddress("Alejandro de valdez y Av. la Gasca 1");
        clientEntity.setWorkAddress("Av. de los Naranjos y Portugal 1");
        clientEntity.setDateOfBirth(LocalDate.parse("1996-01-21"));
        clientEntity.setClientNumber("CLI383EC");
        clientEntity.setVersion(1L);

        Mockito.when(clientRepo.save(Mockito.any(ClientEntity.class)))
                .thenReturn(Mono.just(clientEntity));

        Mockito.when(clientMapper.toEntity(Mockito.any(ClientWrapper.class)))
                .thenReturn(clientEntity);

        ClientWrapper clientWrapper = new ClientWrapper();
        clientWrapper.setIdentification("0401859376");
        clientWrapper.setName("Kevin 1");
        clientWrapper.setLastname("Chamorro 1");
        clientWrapper.setPhone("0996427491");
        clientWrapper.setHomeAddress("Alejandro de valdez y Av. la Gasca 1");
        clientWrapper.setWorkAddress("Av. de los Naranjos y Portugal 1");
        clientWrapper.setDateOfBirth(LocalDate.parse("1996-01-21"));

        Mockito.when(clientMapper.toWrapper(Mockito.any(ClientEntity.class)))
                .thenReturn(clientWrapper);

    }

    @Test
    void saveAllV7() {

        ClientEntity clientEntity = new ClientEntity();
        clientEntity.setId(1L);
        clientEntity.setIdentification("0401859376");
        clientEntity.setName("Kevin 1");
        clientEntity.setLastname("Chamorro 1");
        clientEntity.setPhone("0996427491");
        clientEntity.setHomeAddress("Alejandro de valdez y Av. la Gasca 1");
        clientEntity.setWorkAddress("Av. de los Naranjos y Portugal 1");
        clientEntity.setDateOfBirth(LocalDate.parse("1996-01-21"));
        clientEntity.setClientNumber("CLI383EC");
        clientEntity.setVersion(1L);

        List<ClientWrapper> clientWrapperList = new ArrayList<>();
        clientWrapperList.add(clientMapper.toWrapper(clientEntity));

        StepVerifier
                .create(clientService.saveAllV7(clientWrapperList))
                .consumeNextWith((response) -> {
                    System.out.println(response.toString());
                    assertEquals(response.getIdentification(),"0401859376");
                })
                .expectNextCount(0)
                .verifyComplete();
    }

    @AfterEach
    void tearDown() {
    }
}