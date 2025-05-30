package ru.fav.petcare.grooming.salon.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fav.petcare.grooming.salon.controller.dto.AppointmentDto;
import ru.fav.petcare.grooming.salon.controller.dto.PetDto;
import ru.fav.petcare.grooming.salon.controller.mapper.AppointmentMapper;
import ru.fav.petcare.grooming.salon.controller.request.CreateAppointmentRequest;
import ru.fav.petcare.grooming.salon.controller.request.CreatePetRequest;
import ru.fav.petcare.grooming.salon.entity.*;
import ru.fav.petcare.grooming.salon.exception.UnauthorizedException;
import ru.fav.petcare.grooming.salon.security.AuthUtils;
import ru.fav.petcare.grooming.salon.service.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Tag(name = "Appointment Rest Controller", description = "CRUD операции для записей")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/appointments")
public class AppointmentRestController {
    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    private final ClientService clientService;
    private final ServiceService serviceService;
    private final TimeSlotService timeSlotService;
    private final PetService petService;
    private final AuthUtils authUtils;
    private final ServicePriceService servicePriceService;

    @Operation(summary = "Получить все предстоящие записи текущего клиента",
            description = "Возвращает список всех предстоящих записей текущего аутентифицированного клиента")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка записей",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AppointmentDto.class)))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
    })
    @GetMapping("/upcoming")
    public ResponseEntity<List<AppointmentDto>> getAllUpcomingAppointments() {
        Client client = clientService.findClientById(authUtils.getCurrentClientId());
        List<Appointment> appointments = appointmentService.findUpcomingByClient(client);
        return ResponseEntity.ok(appointments.stream().map(appointmentMapper::toDto).collect(Collectors.toList()));
    }

    @Operation(summary = "Получить все прошедшие записи текущего клиента",
            description = "Возвращает список всех прошедших записей текущего аутентифицированного клиента")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение списка записей",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = AppointmentDto.class)))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован")
    })
    @GetMapping("/passed")
    public ResponseEntity<List<AppointmentDto>> getAllPassedAppointments() {
        Client client = clientService.findClientById(authUtils.getCurrentClientId());
        List<Appointment> appointments = appointmentService.findPassedByClient(client);
        return ResponseEntity.ok(appointments.stream().map(appointmentMapper::toDto).collect(Collectors.toList()));
    }

    @Operation(summary = "Получить запись по ID",
            description = "Возвращает информацию о записи по его идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение данных записи",
                    content = @Content(schema = @Schema(implementation = PetDto.class))),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "403", description = "Нет доступа к этой записи"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDto> getAppointment(@PathVariable Long id) {
        Client client = clientService.findClientById(authUtils.getCurrentClientId());
        Appointment appointment = appointmentService.findById(id);

        if (isNotClientsAppointment(appointment, client)) {
            throw new UnauthorizedException("Нет доступа к этой записи");
        }

        return ResponseEntity.ok(appointmentMapper.toDto(appointment));
    }

    @Operation(summary = "Создать новую запись",
            description = "Создает новую запись для текущего клиента")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Запись успешно создана"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "403", description = "Нет доступа к указанным ресурсам"),
            @ApiResponse(responseCode = "404", description = "Один из ресурсов не найден")
    })
    @PostMapping
    public ResponseEntity<Void> createAppointment(@RequestBody @Valid CreateAppointmentRequest createAppointmentRequest) {
        Client client = clientService.findClientById(authUtils.getCurrentClientId());
        Pet pet = petService.findById(createAppointmentRequest.getPetId());

        if (isNotClientsPet(pet, client)) {
            throw new UnauthorizedException("Нет доступа к этому питомцу");
        }

        Service service = serviceService.findById(createAppointmentRequest.getServiceId());
        TimeSlot timeSlot = timeSlotService.findById(createAppointmentRequest.getTimeSlotId());

        appointmentService.book(pet, service, timeSlot);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "Получить информацию для подтверждения записи",
            description = "Возвращает информацию о питомце, услуге и временном слоте для подтверждения записи")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешное получение информации для подтверждения",
                    content = @Content(schema = @Schema(implementation = AppointmentDto.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "403", description = "Нет доступа к указанным ресурсам"),
            @ApiResponse(responseCode = "404", description = "Один из ресурсов не найден")
    })
    @PostMapping("/confirmation-info")
    public ResponseEntity<AppointmentDto> getConfirmationInfo(
            @RequestBody @Valid CreateAppointmentRequest request) {
        Client client = clientService.findClientById(authUtils.getCurrentClientId());

        Pet pet = petService.findById(request.getPetId());
        if (isNotClientsPet(pet, client)) {
            throw new UnauthorizedException("Нет доступа к указанному питомцу");
        }

        Service service = serviceService.findById(request.getServiceId());
        TimeSlot timeSlot = timeSlotService.findById(request.getTimeSlotId());

        AppointmentDto appointmentDto = appointmentMapper.toDto(pet, service, timeSlot);

        int price = servicePriceService.findPriceForPetAndService(pet, service);
        appointmentDto.setPrice(price);

        return ResponseEntity.ok(appointmentDto);
    }

    @Operation(summary = "Отменить запись по ID",
            description = "Отменяет запись по ее идентификатору")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Запись успешно отменена"),
            @ApiResponse(responseCode = "401", description = "Пользователь не аутентифицирован"),
            @ApiResponse(responseCode = "403", description = "Нет доступа к этой записи"),
            @ApiResponse(responseCode = "404", description = "Запись не найдена")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        Client client = clientService.findClientById(authUtils.getCurrentClientId());
        Appointment appointment = appointmentService.findById(id);

        if (isNotClientsAppointment(appointment, client)) {
            throw new UnauthorizedException("Нет доступа к этой записи");
        }

        appointmentService.cancel(id);
        return ResponseEntity.noContent().build();
    }

    private boolean isNotClientsAppointment(Appointment appointment, Client client) {
        return !Objects.equals(appointment.getPet().getOwner().getId(), client.getId());
    }

    private boolean isNotClientsPet(Pet pet, Client client) {
        return !Objects.equals(pet.getOwner().getId(), client.getId());
    }
}