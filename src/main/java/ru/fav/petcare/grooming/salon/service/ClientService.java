package ru.fav.petcare.grooming.salon.service;

import ru.fav.petcare.grooming.salon.entity.Client;

import java.util.Set;

public interface ClientService {
    Client findClientByPhone(String phone);
    Client createClient(String firstName, String lastName, String phone, String password);
    Client findClientById(Long clientId);
    void updateClientById(Long clientId, String firstName, String lastName, String phone, String password);
    void deleteClientById(Long clientId);
}
