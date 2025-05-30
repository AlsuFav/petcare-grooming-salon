package ru.fav.petcare.grooming.salon.controller.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAppointmentRequest {

    @NotNull
    private long petId;

    @NotNull
    private long serviceId;

    @NotNull
    private long timeSlotId;
}
