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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fav.petcare.grooming.salon.controller.dto.TimeSlotDto;
import ru.fav.petcare.grooming.salon.controller.mapper.TimeSlotMapper;
import ru.fav.petcare.grooming.salon.entity.TimeSlot;
import ru.fav.petcare.grooming.salon.service.TimeSlotService;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "TimeSlot Rest Controller", description = "CRUD операции для временных слотов")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/timeslots")
public class TimeSlotRestController {
    private final TimeSlotService timeSlotService;
    private final TimeSlotMapper timeSlotMapper;

    @Operation(summary = "Получить все доступные временные слоты",
            description = "Возвращает список всех доступных временных слотов")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка временных слотов",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = TimeSlotDto.class))))
    })
    @GetMapping()
    public ResponseEntity<List<TimeSlotDto>> getAll() {

        List<TimeSlot> timeSlots = timeSlotService.findAvailableTimeSlots();
        return ResponseEntity.ok(timeSlots.stream().map(timeSlotMapper::toDto).collect(Collectors.toList()));
    }
}