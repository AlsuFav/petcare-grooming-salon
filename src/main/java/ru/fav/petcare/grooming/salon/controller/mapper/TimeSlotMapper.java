package ru.fav.petcare.grooming.salon.controller.mapper;

import org.springframework.stereotype.Component;
import ru.fav.petcare.grooming.salon.controller.dto.TimeSlotDto;
import ru.fav.petcare.grooming.salon.entity.TimeSlot;

@Component
public class TimeSlotMapper {
    public TimeSlotDto toDto(TimeSlot timeSlot) {
        TimeSlotDto dto = new TimeSlotDto();
        dto.setId(timeSlot.getId());
        dto.setGroomerName(timeSlot.getGroomer().getFirstName());
        dto.setStartTime(timeSlot.getStartTime());
        dto.setEndTime(timeSlot.getEndTime());
        return dto;
    }
}
