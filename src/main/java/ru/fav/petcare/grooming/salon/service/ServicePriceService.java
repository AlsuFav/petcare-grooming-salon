package ru.fav.petcare.grooming.salon.service;


import ru.fav.petcare.grooming.salon.entity.Pet;
import ru.fav.petcare.grooming.salon.entity.Service;
import ru.fav.petcare.grooming.salon.entity.ServicePrice;

import java.util.List;

public interface ServicePriceService {
    List<ServicePrice> findAll();
    List<ServicePrice> findAllInCurrency(String currencyCode);
    int findPriceForPetAndService(Pet pet, Service service);
}
