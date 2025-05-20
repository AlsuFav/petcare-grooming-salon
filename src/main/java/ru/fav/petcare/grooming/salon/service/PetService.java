package ru.fav.petcare.grooming.salon.service;

import ru.fav.petcare.grooming.salon.controller.dto.PetDto;
import ru.fav.petcare.grooming.salon.entity.Pet;

import java.util.List;

public interface PetService {
    Pet findById(Long id);
    List<Pet> findAllByOwnerId(Long ownerId);
    Pet createPet(Long ownerId, PetDto petDto);
    void updatePetById(Long id, PetDto petDto);
    void deletePetById(Long id);
}
