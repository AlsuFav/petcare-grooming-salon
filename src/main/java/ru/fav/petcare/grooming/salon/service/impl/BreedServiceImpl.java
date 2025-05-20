package ru.fav.petcare.grooming.salon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fav.petcare.grooming.salon.entity.Breed;
import ru.fav.petcare.grooming.salon.exception.NotFoundException;
import ru.fav.petcare.grooming.salon.repository.BreedRepository;
import ru.fav.petcare.grooming.salon.service.BreedService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BreedServiceImpl implements BreedService {

    private final BreedRepository breedRepository;

    @Override
    public Breed findBreedById(Long id) {
        return breedRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Порода с ID " + id + " не найдена"));
    }

    @Override
    public List<Breed> findAllBreeds() {
        return breedRepository.findAll();
    }

    @Override
    public boolean isSameBreedType(Long breedId1, Long breedId2) {
        Breed breed1 = findBreedById(breedId1);
        Breed breed2 = findBreedById(breedId2);
        return breed1.getBreedType().equals(breed2.getBreedType());
    }
}
