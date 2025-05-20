package ru.fav.petcare.grooming.salon.service;

import ru.fav.petcare.grooming.salon.controller.dto.ClientDto;
import ru.fav.petcare.grooming.salon.entity.Client;

public interface ClientService {
    Client findClientByPhone(String phone);
    Client createClient(ClientDto clientDto, String password);
    Client findClientById(Long clientId);
    void updateClientById(Long clientId, ClientDto clientDto);
    void changePassword(Long clientId, String currentPassword, String newPassword, String confirmNewPassword);
    void deleteClientById(Long clientId);
}
