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
import org.springframework.web.bind.annotation.*;
import ru.fav.petcare.grooming.salon.controller.dto.BreedDto;
import ru.fav.petcare.grooming.salon.controller.mapper.BreedMapper;
import ru.fav.petcare.grooming.salon.entity.Breed;
import ru.fav.petcare.grooming.salon.service.BreedService;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Breed Rest Controller", description = "CRUD операции для пород")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/breeds")
public class BreedRestController {
    private final BreedService breedService;
    private final BreedMapper breedMapper;

    @Operation(
            summary = "Получить все породы с названием, включающим запрос",
            description = "Возвращает список пород, название которых содержит переданную подстроку"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Успешное получение списка пород",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = BreedDto.class)))
            )
    })
    @GetMapping
    public ResponseEntity<List<BreedDto>> getAllBreedsByNameContainingQuery(@RequestParam String query) {
        List<Breed> breeds = breedService.findBreedByNameContaining(query);
        return ResponseEntity.ok(
                breeds.stream()
                        .map(breedMapper::toDto)
                        .collect(Collectors.toList())
        );
    }
}