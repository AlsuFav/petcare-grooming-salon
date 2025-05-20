package ru.fav.petcare.grooming.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fav.petcare.grooming.salon.entity.Breed;

public interface BreedRepository extends JpaRepository<Breed, Long> {

}
