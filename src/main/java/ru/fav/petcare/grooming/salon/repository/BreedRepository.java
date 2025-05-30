package ru.fav.petcare.grooming.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fav.petcare.grooming.salon.entity.Breed;

import java.util.List;
import java.util.Optional;

public interface BreedRepository extends JpaRepository<Breed, Long> {
    Optional<Breed> findBreedByNameIgnoreCase(String name);

    List<Breed> findAllByNameContainingIgnoreCase(String query);
}
