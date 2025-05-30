package ru.fav.petcare.grooming.salon.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private Long id;

    @NotBlank
    private String petName;

    @NotBlank
    private String groomerName;

    @NotBlank
    private String serviceName;

    @NotBlank
    private int price;

    @NotBlank
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm")
    private LocalDateTime date;

    private boolean upcoming;
}