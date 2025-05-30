package ru.fav.petcare.grooming.salon.service;

import ru.fav.petcare.grooming.salon.entity.*;

import java.util.List;

public interface AppointmentService {
    Appointment findById(Long id);
    void book(Pet pet, Service service, TimeSlot timeSlot);
    void cancel(long appointmentId);
    List<Appointment> findUpcomingByClient(Client client);
    List<Appointment> findPassedByClient(Client client);
    List<Appointment> findUpcomingByPet(Pet pet);
    void updateAppointmentPricesForPet(Pet pet);
}
