package ru.fav.petcare.grooming.salon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.fav.petcare.grooming.salon.entity.BreedTypeEnum;
import ru.fav.petcare.grooming.salon.entity.Service;
import ru.fav.petcare.grooming.salon.entity.ServicePrice;

import java.util.List;
import java.util.Optional;

public interface ServicePriceRepository extends JpaRepository<ServicePrice, Long> {
    Optional<ServicePrice> findByBreedTypeAndService(BreedTypeEnum breedType, Service service);
    List<ServicePrice> findByService(Service service);
}
