package ru.fav.petcare.grooming.salon.service;


import ru.fav.petcare.grooming.salon.entity.Breed;

import java.util.List;

public interface BreedService {
    Breed findBreedById(Long id);
    Breed findBreedByName(String name);
    List<Breed> findAllBreeds();
    boolean isSameBreedType(Long breedId1, Long breedId2);
}
