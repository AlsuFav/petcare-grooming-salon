package ru.fav.petcare.grooming.salon.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.fav.petcare.grooming.salon.controller.dto.PetDto;
import ru.fav.petcare.grooming.salon.entity.Appointment;
import ru.fav.petcare.grooming.salon.entity.Breed;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.entity.Pet;
import ru.fav.petcare.grooming.salon.exception.AppointmentsNotCancelledException;
import ru.fav.petcare.grooming.salon.exception.BadRequestException;
import ru.fav.petcare.grooming.salon.exception.DayInFutureException;
import ru.fav.petcare.grooming.salon.exception.NotFoundException;
import ru.fav.petcare.grooming.salon.repository.PetRepository;
import ru.fav.petcare.grooming.salon.service.AppointmentService;
import ru.fav.petcare.grooming.salon.service.BreedService;
import ru.fav.petcare.grooming.salon.service.ClientService;
import ru.fav.petcare.grooming.salon.service.PetService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final ClientService clientService;
    private final BreedService breedService;
    private final AppointmentService appointmentService;

    @Override
    public Pet findById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Питомец с ID " + id + " не найден"));
    }

    @Override
    public List<Pet> findAllByOwnerId(Long ownerId) {
        return petRepository.findAllByOwnerId(ownerId);
    }

    @Override
    @Transactional
    public Pet createPet(Long ownerId, PetDto petDto) {
        validateBirthDate(petDto.getBirthDate());

        Client owner = clientService.findClientById(ownerId);

        if(! (petDto.getSpecies().equals("Собака") || (petDto.getSpecies().equals("Кошка")))) {
            throw new BadRequestException("Вид питомца должен быть \"Собака\" или \"Кошка\"");
        }

        Breed breed = null;
        if (Objects.equals(petDto.getSpecies(), "Собака")) {
            breed = breedService.findBreedByName(petDto.getBreed());
        }

        if(petDto.getSpecies().equals("Собака") && breed == null) {
            throw new BadRequestException("Необходимо добавить породу собаки");
        }

        Pet pet = new Pet();
        pet.setName(petDto.getName());
        pet.setSpecies(petDto.getSpecies());
        pet.setBreed(breed);
        pet.setBirthDate(petDto.getBirthDate());
        pet.setOwner(owner);

        return petRepository.save(pet);
    }

    @Override
    @Transactional
    public void updatePetById(Long id, PetDto petDto) {
        validateBirthDate(petDto.getBirthDate());

        Pet pet = petRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Питомец с ID " + id + " не найден"));

        pet.setName(petDto.getName());
        pet.setBirthDate(petDto.getBirthDate());

        if (pet.getSpecies().equalsIgnoreCase("собака")) {
            Breed breed = breedService.findBreedByName(petDto.getBreed());
            Breed oldBreed = pet.getBreed();

            pet.setBreed(breed);

            if (!breedService.isSameBreedType(oldBreed.getId(), breed.getId())) {
                try {
                    appointmentService.updateAppointmentPricesForPet(pet);
                } catch (NotFoundException e) {
                    throw new AppointmentsNotCancelledException("Сначала отмените записи для питомца " + pet.getName());
                }
            }
        }

        petRepository.save(pet);
    }

    @Override
    @Transactional
    public void deletePetById(Long id) {
        Pet pet = findById(id);
        List<Appointment> upcomingAppointments = appointmentService.findUpcomingByPet(pet);
        if (!upcomingAppointments.isEmpty()) {
            throw new AppointmentsNotCancelledException("Сначала отмените все записи для питомца.");
        }
        petRepository.deleteById(id);
    }

    private void validateBirthDate(LocalDate birthDate) {
        if (birthDate.isAfter(LocalDate.now())) {
            throw new DayInFutureException();
        }
    }
}
