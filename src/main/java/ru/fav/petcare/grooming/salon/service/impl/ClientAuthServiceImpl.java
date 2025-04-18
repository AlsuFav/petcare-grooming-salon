package ru.fav.petcare.grooming.salon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.exception.InvalidCredentialsException;
import ru.fav.petcare.grooming.salon.exception.PasswordMismatchException;
import ru.fav.petcare.grooming.salon.service.ClientAuthService;
import ru.fav.petcare.grooming.salon.service.ClientService;

@Service
@RequiredArgsConstructor
public class ClientAuthServiceImpl implements ClientAuthService {
    private final ClientService clientService;
    private final AuthenticationManager authenticationManager;

    @Override
    public Client login(String phone, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(phone, password));
        } catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }

        return clientService.findClientByPhone(phone);
    }

    @Override
    public Client register(String firstName, String lastName, String phone, String password, String confirmPassword) {
        if(!password.equals(confirmPassword)) {
            throw new PasswordMismatchException();
        }

        Client client = clientService.createClient(firstName, lastName, phone, password);
        return clientService.findClientByPhone(client.getPhone());
    }
}
