package ru.fav.petcare.grooming.salon.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fav.petcare.grooming.salon.controller.dto.AppointmentDto;
import ru.fav.petcare.grooming.salon.controller.dto.ServiceDto;
import ru.fav.petcare.grooming.salon.controller.mapper.AppointmentMapper;
import ru.fav.petcare.grooming.salon.controller.mapper.ServiceMapper;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.entity.Pet;
import ru.fav.petcare.grooming.salon.entity.Service;
import ru.fav.petcare.grooming.salon.entity.ServicePrice;
import ru.fav.petcare.grooming.salon.exception.UnauthorizedException;
import ru.fav.petcare.grooming.salon.security.AuthUtils;
import ru.fav.petcare.grooming.salon.service.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Tag(name = "Service Rest Controller", description = "CRUD операции для услуг")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/services")
public class ServiceRestController {
    private final ServiceService serviceService;
    private final ServicePriceService servicePriceService;
    private final PetService petService;
    private final ServiceMapper serviceMapper;
    private final ClientService clientService;
    private final AuthUtils authUtils;

    @Operation(summary = "Получить все предстоящие услуги доступные для питомца",
            description = "Возвращает список всех доступных услуг для питомца")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка услуг",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ServiceDto.class)))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "403", description = "Нет доступа к этому питомцу"),
            @ApiResponse(responseCode = "404", description = "Питомец не найден")
    })
    @GetMapping("/for-pet/{id}")
    public ResponseEntity<List<ServiceDto>> getAllForPet(@PathVariable Long id) {
        Client client = clientService.findClientById(authUtils.getCurrentClientId());
        Pet pet = petService.findById(id);

        if (isNotClientsPet(pet, client)) {
            throw new UnauthorizedException("Нет доступа к этому питомцу");
        }

        List<Service> services = serviceService.findAvailableForPet(pet);
        return ResponseEntity.ok(services.stream()
                .map(service -> {
                    ServiceDto dto = serviceMapper.toDto(service);
                    dto.setPrice(servicePriceService.findPriceForPetAndService(pet, service));
                    return dto;
                }
                )
                .collect(Collectors.toList()));
    }

    private boolean isNotClientsPet(Pet pet, Client client) {
        return !Objects.equals(pet.getOwner().getId(), client.getId());
    }
}