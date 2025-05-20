package ru.fav.petcare.grooming.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.fav.petcare.grooming.salon.entity.Service;

import java.util.List;

public interface ServiceRepository extends JpaRepository<Service, Long> {

    @Query("SELECT s FROM Service s " +
            "JOIN ServicePrice sp ON s.id = sp.service.id " +
            "JOIN Pet p ON p.species = sp.species " +
            "LEFT JOIN Breed b ON p.breed.id = b.id " +
            "WHERE p.id = :petId " +
            "AND (sp.breedType IS NULL OR sp.breedType = b.breedType)")
    List<Service> findAvailableServicesForPet(@Param("petId") Long petId);
}
