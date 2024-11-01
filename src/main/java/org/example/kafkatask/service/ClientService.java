package org.example.kafkatask.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.kafkatask.dto.ClientDto;
import org.example.kafkatask.entity.Client;
import org.example.kafkatask.kafka.KafkaClientProducer;
import org.example.kafkatask.repository.ClientRepository;
import org.example.kafkatask.util.ClientMapper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final KafkaClientProducer kafkaClientProducer;
    private final ClientRepository repository;

    public void registerClients(List<Client> clients) {
        log.info("Registering clients.. {}", clients);
        repository.saveAll(clients)
                .stream()
                .map(ClientMapper::toDto)
                .forEach(kafkaClientProducer::send);
    }


    public List<ClientDto> parseJson() {
        ObjectMapper mapper = new ObjectMapper();
        ClientDto[] clients;
        try {
            clients = mapper.readValue(new File("src/main/resources/MOCK_DATA.json"), ClientDto[].class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Arrays.asList(clients);
    }


}
