package ru.fav.petcare.grooming.salon.service;

import ru.fav.petcare.grooming.salon.entity.Groomer;
import ru.fav.petcare.grooming.salon.entity.TimeSlot;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface TimeSlotService {
    TimeSlot findById(Long id);
    Map<LocalDate, List<TimeSlot>> findAvailableTimeSlotsGroupedByDate();
    void setTaken(Long id);
    void setEmptyByStartTimeAndGroomer(LocalDateTime startTime, Groomer groomer);
}
