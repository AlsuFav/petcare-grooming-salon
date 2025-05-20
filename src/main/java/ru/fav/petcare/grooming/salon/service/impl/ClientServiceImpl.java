package ru.fav.petcare.grooming.salon.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.fav.petcare.grooming.salon.controller.dto.ClientDto;
import ru.fav.petcare.grooming.salon.entity.Appointment;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.exception.AppointmentsNotCancelledException;
import ru.fav.petcare.grooming.salon.exception.NotFoundException;
import ru.fav.petcare.grooming.salon.exception.PasswordMismatchException;
import ru.fav.petcare.grooming.salon.exception.ClientAlreadyExistsException;
import ru.fav.petcare.grooming.salon.repository.ClientRepository;
import ru.fav.petcare.grooming.salon.service.AppointmentService;
import ru.fav.petcare.grooming.salon.service.ClientService;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    private final AppointmentService appointmentService;

    @Override
    public Client findClientByPhone(String phone) {
        System.out.println("c");
        return clientRepository.findByPhone(phone)
                .orElseThrow(() -> new NotFoundException("Клиент с телефоном " + phone + " не найден"));
    }

    @Override
    @Transactional
    public Client createClient(ClientDto clientDto, String password) {

        if (clientRepository.findByPhone(clientDto.getPhone()).isPresent()) {
            throw new ClientAlreadyExistsException(clientDto.getPhone());
        }

        String encodedPassword = passwordEncoder.encode(password);

        Client client = new Client(
                clientDto.getFirstName(),
                clientDto.getLastName(),
                clientDto.getPhone(),
                encodedPassword
        );
        return clientRepository.save(client);
    }

    @Override
    public Client findClientById(Long clientId) {
        return clientRepository.findById(clientId).orElseThrow(() -> new NotFoundException("Клиент с ID: " + clientId + " не найден"));
    }

    @Override
    @Transactional
    public void updateClientById(Long clientId, ClientDto clientDto) {
        Client client = clientRepository
                .findById(clientId)
                .orElseThrow(() -> new NotFoundException("Клиент с ID: " + clientId + " не найден"));

        if (!client.getPhone().equals(clientDto.getPhone()) && clientRepository.findByPhone(clientDto.getPhone()).isPresent()) {
            throw new ClientAlreadyExistsException(clientDto.getPhone());
        }

        client.setFirstName(clientDto.getFirstName());
        client.setLastName(clientDto.getLastName());
        client.setPhone(clientDto.getPhone());
        client.setEmail(client.getEmail());

        clientRepository.save(client);
    }

    @Override
    @Transactional
    public void changePassword(Long clientId, String currentPassword, String newPassword, String confirmNewPassword) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new NotFoundException("Клиент не найден"));

        if (!passwordEncoder.matches(currentPassword, client.getPassword())) {
            throw new PasswordMismatchException("Неверный текущий пароль");
        }

        if(!newPassword.equals(confirmNewPassword)) {
            throw new PasswordMismatchException("Пароли не совпадают");
        }

        client.setPassword(passwordEncoder.encode(newPassword));
        clientRepository.save(client);
    }


    @Transactional
    @Override
    public void deleteClientById(Long clientId) {
        Client client = findClientById(clientId);
        List<Appointment> upcomingAppointments = appointmentService.findUpcomingByClient(client);
        if (!upcomingAppointments.isEmpty()) {
            throw new AppointmentsNotCancelledException("Сначала отмените все записи.");
        }

        clientRepository.deleteById(clientId);
    }
}
