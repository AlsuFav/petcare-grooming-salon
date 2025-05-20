package ru.fav.petcare.grooming.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.fav.petcare.grooming.salon.entity.Groomer;
import ru.fav.petcare.grooming.salon.entity.TimeSlot;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {

    Optional<TimeSlot> findByStartTimeAndGroomer(LocalDateTime start, Groomer groomer);

    @Query("SELECT t FROM TimeSlot t WHERE t.taken = false AND t.startTime > CURRENT_TIMESTAMP")
    List<TimeSlot> findAvailableTimeSlots();

    List<TimeSlot> findByGroomer(Groomer groomer);

    @Modifying
    @Query("UPDATE TimeSlot t SET t.taken = true WHERE t.id = :id")
    void setTaken(@Param("id") Long id);

    @Modifying
    @Query("UPDATE TimeSlot t SET t.taken = false WHERE t.id = :id")
    void setEmpty(@Param("id") Long id);
}
