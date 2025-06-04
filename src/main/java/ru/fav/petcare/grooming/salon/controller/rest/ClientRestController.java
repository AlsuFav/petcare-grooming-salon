package ru.fav.petcare.grooming.salon.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fav.petcare.grooming.salon.controller.dto.ClientDto;
import ru.fav.petcare.grooming.salon.controller.mapper.ClientMapper;
import ru.fav.petcare.grooming.salon.controller.request.ChangePasswordRequest;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.security.AuthUtils;
import ru.fav.petcare.grooming.salon.service.ClientService;

@Tag(name = "Client Rest Controller", description = "CRUD операции для клиента")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/client")
public class ClientRestController {
    private final ClientService clientService;
    private final ClientMapper clientMapper;
    private final AuthUtils authUtils;


    @Operation(summary = "Получить данные текущего клиента",
            description = "Возвращает информацию о текущем аутентифицированном клиенте")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение данных клиента",
                    content = @Content(schema = @Schema(implementation = ClientDto.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Клиент не найден",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/me")
    public ResponseEntity<ClientDto> findClient() {
        Long clientId = authUtils.getCurrentClientId();
        Client client = clientService.findClientById(clientId);
        return ResponseEntity.ok(clientMapper.toDto(client));
    }

    @Operation(summary = "Изменение пароля",
            description = "Позволяет текущему клиенту изменить свой пароль")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пароль успешно изменен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные (не совпадают пароли или неверный текущий пароль)",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Клиент не найден",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/me/password")
    public ResponseEntity<Void> changePassword(@RequestBody @Valid ChangePasswordRequest request) {
        Long clientId = authUtils.getCurrentClientId();
        clientService.changePassword(clientId, request.getCurrentPassword(), request.getNewPassword(), request.getConfirmNewPassword());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Обновление данных клиента",
            description = "Позволяет текущему клиенту обновить свои данные")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные успешно обновлены"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Клиент не найден",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Телефон уже занят другим пользователем",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping("/me")
    public ResponseEntity<Void> updateClient(@RequestBody @Valid ClientDto dto) {
        Long clientId = authUtils.getCurrentClientId();
        clientService.updateClientById(clientId, dto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удаление аккаунта",
            description = "Позволяет текущему клиенту удалить свой аккаунт")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Аккаунт успешно удален"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Клиент не найден",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteClient() {
        Long clientId = authUtils.getCurrentClientId();
        clientService.deleteClientById(clientId);
        return ResponseEntity.noContent().build();
    }
}
