package ru.fav.petcare.grooming.salon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fav.petcare.grooming.salon.entity.BreedTypeEnum;
import ru.fav.petcare.grooming.salon.entity.Pet;
import ru.fav.petcare.grooming.salon.entity.ServicePrice;
import ru.fav.petcare.grooming.salon.exception.NotFoundException;
import ru.fav.petcare.grooming.salon.repository.ServicePriceRepository;
import ru.fav.petcare.grooming.salon.service.ServicePriceService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServicePriceServiceImpl implements ServicePriceService {

    private final ServicePriceRepository servicePriceRepository;

    @Override
    public List<ServicePrice> findAll() {
        return servicePriceRepository.findAll();
    }

    @Override
    public int findPriceForPetAndService(Pet pet, ru.fav.petcare.grooming.salon.entity.Service service) {
        BreedTypeEnum breedType = pet.getBreed().getBreedType();

        return servicePriceRepository.findByBreedTypeAndService(breedType, service)
                .map(ServicePrice::getPrice)
                .orElseThrow(() -> new NotFoundException("Цена для породы " + breedType + " и услуги " + service.getName() + " не найдена"));
    }
}
