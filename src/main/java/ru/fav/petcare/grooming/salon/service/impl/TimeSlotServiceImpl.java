package ru.fav.petcare.grooming.salon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fav.petcare.grooming.salon.entity.Groomer;
import ru.fav.petcare.grooming.salon.entity.TimeSlot;
import ru.fav.petcare.grooming.salon.exception.NotFoundException;
import ru.fav.petcare.grooming.salon.repository.TimeSlotRepository;
import ru.fav.petcare.grooming.salon.service.TimeSlotService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;

    @Override
    public TimeSlot findById(Long id) {
        return timeSlotRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Временной слот с ID " + id + " не найден"));
    }

    @Override
    public Map<LocalDate, List<TimeSlot>> findAvailableTimeSlotsGroupedByDate() {
        return timeSlotRepository.findAvailableTimeSlots().stream()
                .collect(Collectors.groupingBy(slot -> slot.getStartTime().toLocalDate()));
    }

    @Override
    @Transactional
    public void setTaken(Long id) {
        timeSlotRepository.setTaken(id);
    }

    @Override
    @Transactional
    public void setEmptyByStartTimeAndGroomer(LocalDateTime startTime, Groomer groomer) {
        Optional<TimeSlot> optionalTimeSlot = timeSlotRepository.findByStartTimeAndGroomer(startTime, groomer);
        if (optionalTimeSlot.isPresent()) {
            timeSlotRepository.setEmpty(optionalTimeSlot.get().getId());
        } else {
            throw new NotFoundException("Временной слот не найден для заданных времени и грумера");
        }
    }
}
