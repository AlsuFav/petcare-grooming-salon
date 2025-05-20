package ru.fav.petcare.grooming.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fav.petcare.grooming.salon.entity.Service;

public interface ServiceRepository extends JpaRepository<Service, Long> {

}
