package ru.fav.petcare.grooming.salon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.fav.petcare.grooming.salon.api.service.CurrencyConverterService;
import ru.fav.petcare.grooming.salon.entity.BreedTypeEnum;
import ru.fav.petcare.grooming.salon.entity.Pet;
import ru.fav.petcare.grooming.salon.entity.ServicePrice;
import ru.fav.petcare.grooming.salon.exception.NotFoundException;
import ru.fav.petcare.grooming.salon.repository.ServicePriceRepository;
import ru.fav.petcare.grooming.salon.service.ServicePriceService;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ServicePriceServiceImpl implements ServicePriceService {

    private final ServicePriceRepository servicePriceRepository;
    private final CurrencyConverterService currencyConverterService;

    @Override
    public List<ServicePrice> findAll() {
        return servicePriceRepository.findAll();
    }

    @Override
    public List<ServicePrice> findAllInCurrency(String currencyCode) {
        List<ServicePrice> prices = servicePriceRepository.findAll();
        if (Objects.equals(currencyCode, "RUB")) {
            return prices;
        } else {
            return prices.stream()
                    .map(price -> convertPrice(price, currencyCode))
                    .collect(Collectors.toList());
        }
    }


    @Override
    public ServicePrice findForPetAndService(Pet pet, ru.fav.petcare.grooming.salon.entity.Service service) {
        BreedTypeEnum breedType = pet.getBreed() != null? pet.getBreed().getBreedType() : null;

        return servicePriceRepository.findByBreedTypeAndService(breedType, service)
                .orElseThrow(() -> new NotFoundException("Цена для породы " + breedType + " и услуги " + service.getName() + " не найдена"));
    }

    @Override
    public List<ServicePrice> findForService(ru.fav.petcare.grooming.salon.entity.Service service) {
        return servicePriceRepository.findByService(service);
    }

    private ServicePrice convertPrice(ServicePrice original, String currencyCode) {
        ServicePrice converted = new ServicePrice();
        converted.setId(original.getId());
        converted.setService(original.getService());
        converted.setSpecies(original.getSpecies());
        converted.setBreedType(original.getBreedType());
        converted.setPrice(currencyConverterService.convertRubToCurrency(original.getPrice(), currencyCode));
        return converted;
    }
}
