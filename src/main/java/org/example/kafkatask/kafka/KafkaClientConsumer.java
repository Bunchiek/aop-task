package org.example.kafkatask.kafka;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.kafkatask.dto.ClientDto;
import org.example.kafkatask.entity.Client;
import org.example.kafkatask.service.ClientService;
import org.example.kafkatask.util.ClientMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaClientConsumer {

    private final ClientService clientService;

    @KafkaListener(id = "${t1.kafka.consumer.group-id}",
            topics = "${t1.kafka.topic.client_registration}",
            containerFactory = "kafkaListenerContainerFactory")
    public void listener(@Payload List<ClientDto> messageList,
                         Acknowledgment ack) {
        log.debug("Client consumer: Обработка новых сообщений");
        List<Client> clients = messageList.stream()
                .map(ClientMapper::toEntity)
                .toList();
        clientService.registerClients(clients);
        ack.acknowledge();
        log.debug("Client consumer: записи обработаны");
    }
}
