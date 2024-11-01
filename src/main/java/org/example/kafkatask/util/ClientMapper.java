package org.example.kafkatask.util;

import lombok.experimental.UtilityClass;
import org.example.kafkatask.dto.ClientDto;
import org.example.kafkatask.entity.Client;


@UtilityClass
public class ClientMapper {

    public static Client toEntity(ClientDto dto) {
        return Client.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .middleName(dto.getMiddleName())
                .email(dto.getEmail())
                .gender(dto.getGender())
                .ipAddress(dto.getIpAddress())
                .build();
    }

    public static ClientDto toDto(Client entity) {
        return ClientDto.builder()
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .middleName(entity.getMiddleName())
                .email(entity.getEmail())
                .gender(entity.getGender())
                .ipAddress(entity.getIpAddress())
                .id(entity.getId())
                .build();
    }

}
