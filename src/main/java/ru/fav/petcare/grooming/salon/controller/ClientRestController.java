package ru.fav.petcare.grooming.salon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fav.petcare.grooming.salon.controller.dto.ClientDto;
import ru.fav.petcare.grooming.salon.controller.dto.UpdateClientDto;
import ru.fav.petcare.grooming.salon.controller.mapper.ClientMapper;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.exception.UnauthorizedAccessException;
import ru.fav.petcare.grooming.salon.repository.ClientRepository;
import ru.fav.petcare.grooming.salon.security.AuthUtils;
import ru.fav.petcare.grooming.salon.service.ClientService;

@Tag(name = "Client Rest Controller", description = "CRUD операции для клиента")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/client")
public class ClientRestController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final AuthUtils authUtils;

    @GetMapping("/{clientId}")
    public ResponseEntity<ClientDto> findClientById(@PathVariable Long clientId) {
        Long currentClientId = authUtils.getCurrentClientId();
        if (!clientId.equals(currentClientId)) {
            throw new UnauthorizedAccessException("Вы не можете получить доступ к чужим данным");
        }

        Client client = clientService.findClientById(clientId);
        return ResponseEntity.ok(clientMapper.toDto(client));
    }

//    @PutMapping("/{clientId}")
//    public ResponseEntity<ClientDto> updateClientById(@PathVariable Long clientId,
//                                                 @Valid @RequestBody UpdateClientDto updateClientDto) {
//        Long currentClientId = authUtils.getCurrentClientId();
//        if (!clientId.equals(currentClientId)) {
//            throw new UnauthorizedAccessException("Вы не можете получить доступ к чужим данным");
//        }
//
//        clientService.updateClientById(clientId, updateClientDto.getFirstName(),
//                updateClientDto.getLastName(), updateClientDto.getPhone(), updateClientDto.getPassword());
//
//        Client updatedClient = clientService.findClientById(clientId);
//        ClientDto response = clientMapper.toDto(updatedClient);
//        return ResponseEntity.ok(response);
//    }


    @DeleteMapping("/{clientId}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long clientId) {
        Long currentClientId = authUtils.getCurrentClientId();
        if (!clientId.equals(currentClientId)) {
            throw new UnauthorizedAccessException("Вы не можете получить доступ к чужим данным");
        }

        clientService.deleteClientById(clientId);
        return ResponseEntity.noContent().build();
    }
}
