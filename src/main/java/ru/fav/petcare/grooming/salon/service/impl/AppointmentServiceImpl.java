package ru.fav.petcare.grooming.salon.service.impl;

import lombok.RequiredArgsConstructor;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.fav.petcare.grooming.salon.entity.Appointment;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.entity.Pet;
import ru.fav.petcare.grooming.salon.entity.TimeSlot;
import ru.fav.petcare.grooming.salon.exception.NotFoundException;
import ru.fav.petcare.grooming.salon.repository.AppointmentRepository;
import ru.fav.petcare.grooming.salon.service.AppointmentService;
import ru.fav.petcare.grooming.salon.service.ServicePriceService;
import ru.fav.petcare.grooming.salon.service.TimeSlotService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final TimeSlotService timeSlotService;
    private final ServicePriceService servicePriceService;

    @Override
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Запись с ID " + id + " не найдена"));
    }

    @Override
    public void book(Pet pet, ru.fav.petcare.grooming.salon.entity.Service service, TimeSlot timeSlot, int price) {
        Appointment appointment = new Appointment();
        appointment.setPet(pet);
        appointment.setGroomer(timeSlot.getGroomer());
        appointment.setService(service);
        appointment.setPrice(price);
        appointment.setDate(timeSlot.getStartTime());

        appointmentRepository.save(appointment);
        timeSlotService.setTaken(timeSlot.getId());
    }

    @Override
    @Transactional
    public void cancel(long id) {
        Appointment appointment = findById(id);
        timeSlotService.setEmptyByStartTimeAndGroomer(appointment.getDate(), appointment.getGroomer());

        appointmentRepository.deleteById(appointment.getId());
    }

    @Override
    public List<Appointment> findUpcomingByClient(Client client) {
        return appointmentRepository.findUpcomingByClientId(client.getId());
    }

    @Override
    public List<Appointment> findUpcomingByPet(Pet pet) {
        return appointmentRepository.findUpcomingByPetId(pet.getId());
    }

    @Override
    @Transactional
    public void updateAppointmentPricesForPet(Pet pet) {
        List<Appointment> appointments = appointmentRepository.findUpcomingByPetId(pet.getId());

        for (Appointment appointment : appointments) {
            int price = servicePriceService.findPriceForPetAndService(pet, appointment.getService());

            appointment.setPrice(price);
            appointmentRepository.save(appointment);
        }
    }
}
