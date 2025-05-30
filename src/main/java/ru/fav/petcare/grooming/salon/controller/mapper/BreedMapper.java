package ru.fav.petcare.grooming.salon.controller.mapper;

import org.springframework.stereotype.Component;
import ru.fav.petcare.grooming.salon.controller.dto.BreedDto;
import ru.fav.petcare.grooming.salon.entity.Breed;

@Component
public class BreedMapper {

    public BreedDto toDto(Breed breed) {
        BreedDto dto = new BreedDto();
        dto.setName(breed.getName());
        return dto;
    }
}