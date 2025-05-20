package ru.fav.petcare.grooming.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fav.petcare.grooming.salon.entity.Groomer;

public interface GroomerRepository extends JpaRepository<Groomer, Long> {
    
}
