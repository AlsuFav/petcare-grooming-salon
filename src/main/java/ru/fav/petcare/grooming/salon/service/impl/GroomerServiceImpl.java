package ru.fav.petcare.grooming.salon.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fav.petcare.grooming.salon.entity.Groomer;
import ru.fav.petcare.grooming.salon.exception.NotFoundException;
import ru.fav.petcare.grooming.salon.repository.GroomerRepository;
import ru.fav.petcare.grooming.salon.service.GroomerService;

import java.util.List;

@Service
@AllArgsConstructor
public class GroomerServiceImpl implements GroomerService {

    private final GroomerRepository groomerRepository;

    @Override
    public Groomer findById(long id) {
        return groomerRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Грумер с ID: " + id + " не найден" ));
    }

    @Override
    public List<Groomer> findAll() {
        return groomerRepository.findAll();
    }

}