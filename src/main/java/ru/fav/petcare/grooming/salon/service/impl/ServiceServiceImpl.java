package ru.fav.petcare.grooming.salon.service.impl;

import lombok.AllArgsConstructor;
import ru.fav.petcare.grooming.salon.entity.Service;
import ru.fav.petcare.grooming.salon.exception.NotFoundException;
import ru.fav.petcare.grooming.salon.repository.ServiceRepository;
import ru.fav.petcare.grooming.salon.service.ServiceService;

import java.util.List;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServiceServiceImpl implements ServiceService {

    private final ServiceRepository serviceRepository;

    @Override
    public Service findById(long id) {
        return serviceRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Услуга с ID: " + id + " не найдена" ));
    }

    @Override
    public List<Service> findAll() {
        return serviceRepository.findAll();
    }

}