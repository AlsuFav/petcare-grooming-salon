package ru.fav.petcare.grooming.salon.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.exception.BadRequestException;
import ru.fav.petcare.grooming.salon.exception.NotFoundException;
import ru.fav.petcare.grooming.salon.exception.PasswordMismatchException;
import ru.fav.petcare.grooming.salon.exception.ClientAlreadyExistsException;
import ru.fav.petcare.grooming.salon.repository.ClientRepository;
import ru.fav.petcare.grooming.salon.service.ClientService;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Client findClientByPhone(String phone) {
        System.out.println("c");
        return clientRepository.findByPhone(phone)
                .orElseThrow(() -> new NotFoundException("Клиент с телефоном " + phone + " не найден"));
    }

    @Override
    @Transactional
    public Client createClient(String firstName, String lastName, String phone, String password) {

        if (clientRepository.findByPhone(phone).isPresent()) {
            throw new ClientAlreadyExistsException(phone);
        }

        String encodedPassword = passwordEncoder.encode(password);

        Client client = new Client(firstName, lastName, phone, encodedPassword);
        return clientRepository.save(client);
    }

    @Override
    public Client findClientById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("Клиент с ID: " + clientId + " не найден"));
    }

    @Override
    @Transactional
    public void updateClientById(Long clientId, String firstName, String lastName, String phone, String password) {
        Client client = clientRepository
                .findById(clientId)
                .orElseThrow(() -> new NotFoundException("Клиент с ID: " + clientId + " не найден"));

        if (!client.getPhone().equals(phone) && clientRepository.findByPhone(phone).isPresent()) {
            throw new ClientAlreadyExistsException(phone);
        }

        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setPhone(phone);

        if (password != null && !password.isEmpty()) {
            client.setPassword(passwordEncoder.encode(password));
        }

        clientRepository.save(client);
    }

    @Transactional
    @Override
    public void deleteClientById(Long clientId) {
        clientRepository.deleteById(clientId);
    }
}
