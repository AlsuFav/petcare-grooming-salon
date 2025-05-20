package ru.fav.petcare.grooming.salon.service;

import ru.fav.petcare.grooming.salon.entity.Service;

import java.util.List;

public interface ServiceService {
    Service findById(long id);
    List<Service> findAll();
}