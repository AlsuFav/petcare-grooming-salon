package ru.fav.petcare.grooming.salon.controller.web;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.fav.petcare.grooming.salon.controller.dto.PetDto;
import ru.fav.petcare.grooming.salon.controller.mapper.PetMapper;
import ru.fav.petcare.grooming.salon.entity.Appointment;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.entity.Pet;
import ru.fav.petcare.grooming.salon.exception.AppointmentsNotCancelledException;
import ru.fav.petcare.grooming.salon.service.AppointmentService;
import ru.fav.petcare.grooming.salon.service.BreedService;
import ru.fav.petcare.grooming.salon.service.PetService;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class PetController {

    private final PetService petService;
    private final PetMapper petMapper;
    private final BreedService breedService;
    private final AppointmentService appointmentService;

    @GetMapping("/addPet")
    public String showAddPetForm(Model model) {
        model.addAttribute("breeds", breedService.findAllBreeds());
        model.addAttribute("petDto", new PetDto());
        return "pet/addPet";
    }

    @PostMapping("/addPet")
    public String addPet(@ModelAttribute("petDto") PetDto petDto,
                         HttpSession session) {
        Client client = (Client) session.getAttribute("client");

        petService.createPet(client.getId(), petDto);
        return "redirect:/clientProfile";

    }

    @GetMapping("/editPet")
    public String showEditPetForm(@RequestParam("petId") Long petId, Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        Pet pet = petService.findById(petId);

        if(isNotClientsPet(pet, client)) {
            return "redirect:/clientProfile";
        }

        model.addAttribute("pet", pet);

        if ("собака".equalsIgnoreCase(pet.getSpecies())) {
            model.addAttribute("breeds", breedService.findAllBreeds());
        }

        return "pet/editPet";
    }

    @PostMapping("/editPet")
    public String updatePet(@RequestParam("petId") Long petId,
                            @ModelAttribute("petDto") PetDto petDto,
                            RedirectAttributes redirectAttributes,
                            HttpSession session) {

        Client client = (Client) session.getAttribute("client");
        Pet pet = petService.findById(petId);

        if (isNotClientsPet(pet, client)) {
            return "redirect:/clientProfile";
        }

        try {
            petService.updatePetById(petId, petDto);
            return "redirect:/petProfile?petId=" + petId;
        } catch (AppointmentsNotCancelledException e) {
            redirectAttributes.addFlashAttribute("error", "cancel_appointments_first");
            return "redirect:/editPet?petId=" + petId;
        }
    }

    @PostMapping("/deletePet")
    public String deletePet(@RequestParam("petId") Long petId,
                            RedirectAttributes redirectAttributes,
                            HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        Pet pet = petService.findById(petId);

        if(isNotClientsPet(pet, client)) {
            return "redirect:/clientProfile";
        }

        try {
            petService.deletePetById(petId);
            return "redirect:/clientProfile";
        } catch (AppointmentsNotCancelledException e) {
            redirectAttributes.addFlashAttribute("error", "cancel_appointments_first");
            return "redirect:/petProfile?petId=" + petId;
        }
    }

    @GetMapping("/petProfile")
    public String viewPetProfile(@RequestParam("petId") Long petId, Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("client");
        Pet pet = petService.findById(petId);

        if(isNotClientsPet(pet, client)) {
            return "redirect:/clientProfile";
        }

        List<Appointment> appointments = appointmentService.findUpcomingByPet(pet);

        model.addAttribute("pet", pet);
        model.addAttribute("upcomingAppointments", appointments);
        return "pet/petProfile";
    }

    private boolean isNotClientsPet(Pet pet, Client client) {
        return !Objects.equals(pet.getOwner().getId(), client.getId());
    }
}
