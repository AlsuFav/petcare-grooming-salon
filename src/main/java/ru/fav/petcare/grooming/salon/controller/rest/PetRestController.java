package ru.fav.petcare.grooming.salon.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.fav.petcare.grooming.salon.controller.dto.PetDto;
import ru.fav.petcare.grooming.salon.controller.mapper.PetMapper;
import ru.fav.petcare.grooming.salon.controller.request.CreatePetRequest;
import ru.fav.petcare.grooming.salon.controller.request.UpdatePetRequest;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.entity.Pet;
import ru.fav.petcare.grooming.salon.exception.UnauthorizedException;
import ru.fav.petcare.grooming.salon.security.AuthUtils;
import ru.fav.petcare.grooming.salon.service.ClientService;
import ru.fav.petcare.grooming.salon.service.FileStorageService;
import ru.fav.petcare.grooming.salon.service.PetService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Tag(name = "Pet Rest Controller", description = "CRUD операции для питомцев")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pets")
public class PetRestController {
    private final ClientService clientService;
    private final PetService petService;
    private final PetMapper petMapper;
    private final FileStorageService fileStorageService;
    private final AuthUtils authUtils;

    @Operation(summary = "Получить питомца по ID",
            description = "Возвращает информацию о питомце по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение данных питомца",
                    content = @Content(schema = @Schema(implementation = PetDto.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к этому питомцу",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Питомец не найден",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PetDto> getPet(@PathVariable Long id) {
        Client client = clientService.findClientById(authUtils.getCurrentClientId());
        Pet pet = petService.findById(id);

        if (isNotClientsPet(pet, client)) {
            throw new UnauthorizedException("Нет доступа к этому питомцу");
        }
        
        return ResponseEntity.ok(petMapper.toDto(pet));
    }

    @Operation(summary = "Получить всех питомцев текущего клиента",
            description = "Возвращает список всех питомцев текущего аутентифицированного клиента")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка питомцев",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = PetDto.class)))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping
    public ResponseEntity<List<PetDto>> getAllPets() {
        Long clientId = authUtils.getCurrentClientId();
        List<Pet> pets = petService.findAllByOwnerId(clientId);
        return ResponseEntity.ok(pets.stream().map(petMapper::toDto).collect(Collectors.toList()));
    }

    @Operation(summary = "Создать нового питомца",
            description = "Создает нового питомца для текущего клиента")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Питомец успешно создан"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Порода не найдена",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping
    public ResponseEntity<Void> createPet(@RequestBody @Valid CreatePetRequest createPetRequest) {
        Long clientId = authUtils.getCurrentClientId();
        PetDto petDto = new PetDto();
        petDto.setName(createPetRequest.getName());
        petDto.setSpecies(createPetRequest.getSpecies());
        petDto.setBirthDate(createPetRequest.getBirthDate());

        String breed = petDto.getSpecies().equals("Собака") ? createPetRequest.getBreed() : null;
        petDto.setBreed(breed);

        petService.createPet(clientId, petDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Обновить данные питомца",
            description = "Обновляет информацию о питомце")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные успешно обновлены"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к этому питомцу",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Питомец или порода не найдены",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })

    @PutMapping("/{id}")
    public ResponseEntity<Void> updatePet(@PathVariable long id, @RequestBody @Valid UpdatePetRequest updatePetRequest) {
        Client client = clientService.findClientById(authUtils.getCurrentClientId());
        Pet pet = petService.findById(id);

        if (isNotClientsPet(pet, client)) {
            throw new UnauthorizedException("Нет доступа к этому питомцу");
        }

        PetDto petDto = petMapper.toDto(pet);
        petDto.setName(updatePetRequest.getName());
        petDto.setBirthDate(updatePetRequest.getBirthDate());
        
        petService.updatePetById(pet.getId(), petDto);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Удалить питомца",
            description = "Удаляет питомца по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Питомец успешно удален"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Нет доступа к этому питомцу",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Питомец не найден",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePet(@PathVariable Long id) {
        Client client = clientService.findClientById(authUtils.getCurrentClientId());
        Pet pet = petService.findById(id);

        if (isNotClientsPet(pet, client)) {
            throw new UnauthorizedException("Нет доступа к этому питомцу");
        }
        
        petService.deletePetById(id);
        return ResponseEntity.noContent().build();
    }

    private boolean isNotClientsPet(Pet pet, Client client) {
        return !Objects.equals(pet.getOwner().getId(), client.getId());
    }
}