package ru.fav.petcare.grooming.salon.service;

import ru.fav.petcare.grooming.salon.entity.Groomer;

import java.util.List;

public interface GroomerService {
    Groomer findById(long id);
    List<Groomer> findAll();
}
