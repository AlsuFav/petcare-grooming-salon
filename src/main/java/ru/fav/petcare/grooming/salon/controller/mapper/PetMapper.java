package ru.fav.petcare.grooming.salon.controller.mapper;

import org.springframework.stereotype.Component;
import ru.fav.petcare.grooming.salon.controller.dto.PetDto;
import ru.fav.petcare.grooming.salon.entity.Pet;

@Component
public class PetMapper {

    public PetDto toDto(Pet pet) {
        PetDto dto = new PetDto();
        dto.setId(pet.getId());
        dto.setName(pet.getName());
        dto.setSpecies(pet.getSpecies());
        dto.setBirthDate(pet.getBirthDate());
        dto.setBreed(pet.getBreed() != null? pet.getBreed().getName() : null);
        dto.setImagePath(pet.getImagePath());
        return dto;
    }
}