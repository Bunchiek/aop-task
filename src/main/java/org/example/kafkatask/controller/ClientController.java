package org.example.kafkatask.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.kafkatask.dto.ClientDto;
import org.example.kafkatask.kafka.KafkaClientProducer;
import org.example.kafkatask.service.ClientService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ClientController {

    private final ClientService clientService;
    private final KafkaClientProducer kafkaClientProducer;
    @Value("${t1.kafka.topic.client_registration}")
    private String topic;


    @Scheduled(fixedRate = 5000)
    public void parseSource() {
        List<ClientDto> clientDtos = clientService.parseJson();
        clientDtos.forEach(dto -> kafkaClientProducer.sendTo(topic, dto));
    }
}
