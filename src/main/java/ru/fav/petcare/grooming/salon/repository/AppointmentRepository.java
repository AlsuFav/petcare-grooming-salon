package ru.fav.petcare.grooming.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.fav.petcare.grooming.salon.entity.Appointment;
import ru.fav.petcare.grooming.salon.entity.Service;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT a FROM Appointment a WHERE a.pet.owner.id = :clientId AND a.date > CURRENT_TIMESTAMP")
    List<Appointment> findUpcomingByClientId(@Param("clientId") Long clientId);

    @Query("SELECT a FROM Appointment a WHERE a.pet.id = :petId AND a.date > CURRENT_TIMESTAMP")
    List<Appointment> findUpcomingByPetId(@Param("petId") Long petId);
}
