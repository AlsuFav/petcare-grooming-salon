package ru.fav.petcare.grooming.salon.controller.web;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.fav.petcare.grooming.salon.entity.*;
import ru.fav.petcare.grooming.salon.service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class AppointmentController {

    private final PetService petService;
    private final ServiceService serviceService;
    private final TimeSlotService timeSlotService;
    private final ServicePriceService servicePriceService;
    private final AppointmentService appointmentService;

    @GetMapping("/createAppointment")
    public String showAppointmentPage(Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        model.addAttribute("pets", petService.findAllByOwnerId(client.getId()));
        return "appointment/appointmentForm";
    }

    @GetMapping("/availableServices")
    @ResponseBody
    public List<Service> getServicesForPet(@RequestParam Long petId) {
        Pet pet = petService.findById(petId);
        return serviceService.findAvailableForPet(pet);
    }

    @GetMapping("/availableTimeslots")
    @ResponseBody
    public Map<LocalDate, List<TimeSlot>> getTimeSlots() {
        return timeSlotService.findAvailableTimeSlotsGroupedByDate();
    }

    @GetMapping("/calculatePrice")
    @ResponseBody
    public int calculatePrice(
            @RequestParam Long petId,
            @RequestParam Long serviceId) {
        Pet pet = petService.findById(petId);
        Service service = serviceService.findById(serviceId);
        return servicePriceService.findForPetAndService(pet, service).getPrice();
    }

    @PostMapping("/createAppointment")
    public String createAppointment(
            @RequestParam Long petId,
            @RequestParam Long serviceId,
            @RequestParam Long timeSlotId,
            HttpSession session) {

        Client client = (Client) session.getAttribute("client");
        Pet pet = petService.findById(petId);
        Service service = serviceService.findById(serviceId);
        TimeSlot timeSlot = timeSlotService.findById(timeSlotId);

        appointmentService.book(pet, service, timeSlot);
        return "redirect:/clientProfile";
    }

    @GetMapping("/appointmentDetails")
    public String cancelAppointment(Model model, @RequestParam("appointmentId") Long appointmentId, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        Appointment appointment = appointmentService.findById(appointmentId);

        if(isNotClientsAppointment(appointment, client)) {
            return "redirect:/clientProfile";
        }

        model.addAttribute(appointment);
        return "appointment/appointmentDetails";
    }

    @PostMapping("/cancelAppointment")
    public String cancelAppointment(@RequestParam("appointmentId") Long appointmentId, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        Appointment appointment = appointmentService.findById(appointmentId);

        if(isNotClientsAppointment(appointment, client)) {
            return "redirect:/clientProfile";
        }

        appointmentService.cancel(appointmentId);
        return "redirect:/clientProfile";
    }

    private boolean isNotClientsAppointment(Appointment appointment, Client client) {
        return !Objects.equals(appointment.getPet().getOwner().getId(), client.getId());
    }
}