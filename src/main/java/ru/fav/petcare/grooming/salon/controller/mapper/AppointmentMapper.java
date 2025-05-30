package ru.fav.petcare.grooming.salon.controller.mapper;

import org.springframework.stereotype.Component;
import ru.fav.petcare.grooming.salon.controller.dto.AppointmentDto;
import ru.fav.petcare.grooming.salon.entity.Appointment;
import ru.fav.petcare.grooming.salon.entity.Pet;
import ru.fav.petcare.grooming.salon.entity.Service;
import ru.fav.petcare.grooming.salon.entity.TimeSlot;

import java.time.LocalDateTime;

@Component
public class AppointmentMapper {
    public AppointmentDto toDto(Appointment appointment) {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(appointment.getId());
        dto.setPetName(appointment.getPet().getName());
        dto.setGroomerName(appointment.getGroomer().getFirstName());
        dto.setServiceName(appointment.getService().getName());
        dto.setDate(appointment.getDate());
        dto.setPrice(appointment.getPrice());
        dto.setUpcoming(appointment.getDate().isAfter(LocalDateTime.now()));
        return dto;
    }

    public AppointmentDto toDto(Pet pet, Service service, TimeSlot timeSlot) {
        AppointmentDto dto = new AppointmentDto();
        dto.setPetName(pet.getName());
        dto.setGroomerName(timeSlot.getGroomer().getFirstName());
        dto.setServiceName(service.getName());
        dto.setDate(timeSlot.getStartTime());
        dto.setUpcoming(timeSlot.getStartTime().isAfter(LocalDateTime.now()));
        return dto;
    }
}
