package ru.fav.petcare.grooming.salon.controller.mapper;

import org.springframework.stereotype.Component;
import ru.fav.petcare.grooming.salon.controller.dto.ClientDto;
import ru.fav.petcare.grooming.salon.entity.Client;

@Component
public class ClientMapper {

    public ClientDto toDto(Client client) {
        ClientDto dto = new ClientDto();
        dto.setFirstName(client.getFirstName());
        dto.setLastName(client.getLastName());
        dto.setPhone(client.getPhone());
        return dto;
    }

    public Client toEntity(ClientDto dto) {
        Client client = new Client();
        client.setFirstName(dto.getFirstName());
        client.setLastName(dto.getLastName());
        client.setPhone(dto.getPhone());
        // password not included here for security reasons — set it elsewhere if needed
        return client;
    }
}