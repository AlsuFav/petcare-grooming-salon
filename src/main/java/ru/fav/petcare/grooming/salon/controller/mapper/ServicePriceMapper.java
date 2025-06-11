package ru.fav.petcare.grooming.salon.controller.mapper;

import org.springframework.stereotype.Component;
import ru.fav.petcare.grooming.salon.controller.dto.ServicePriceDto;
import ru.fav.petcare.grooming.salon.entity.ServicePrice;

@Component
public class ServicePriceMapper {

    public ServicePriceDto toDto(ServicePrice servicePrice) {
        ServicePriceDto dto = new ServicePriceDto();
        dto.setSpecies(servicePrice.getSpecies());

        String breedType = servicePrice.getBreedType() != null ?servicePrice.getBreedType().getTitle() : null;
        dto.setBreedType(breedType);

        dto.setPrice(servicePrice.getPrice());
        return dto;
    }
}