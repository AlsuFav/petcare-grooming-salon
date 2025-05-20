package ru.fav.petcare.grooming.salon.controller.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.fav.petcare.grooming.salon.controller.dto.ClientDto;
import ru.fav.petcare.grooming.salon.controller.mapper.ClientMapper;
import ru.fav.petcare.grooming.salon.controller.request.ChangePasswordRequest;
import ru.fav.petcare.grooming.salon.entity.Appointment;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.entity.Pet;
import ru.fav.petcare.grooming.salon.exception.AppointmentsNotCancelledException;
import ru.fav.petcare.grooming.salon.exception.ClientAlreadyExistsException;
import ru.fav.petcare.grooming.salon.exception.PasswordMismatchException;
import ru.fav.petcare.grooming.salon.service.AppointmentService;
import ru.fav.petcare.grooming.salon.service.ClientService;
import ru.fav.petcare.grooming.salon.service.PetService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final AppointmentService appointmentService;
    private final PetService petService;
    private final ClientService clientService;

    @GetMapping("/clientProfile")
    public String getClientProfile(Model model, HttpSession session) {
        Client client = (Client) session.getAttribute("client");

        List<Pet> pets = petService.findAllByOwnerId(client.getId());
        List<Appointment> appointments = appointmentService.findUpcomingByClient(client);

        model.addAttribute("pets", pets);
        model.addAttribute("upcomingAppointments", appointments);
        return "client/clientProfile";
    }

    @GetMapping("/editProfile")
    public String showEditForm() {
        return "client/clientEdit";
    }

    @PostMapping("/editProfile")
    public String updateProfile(
            @ModelAttribute("clientDto") ClientDto clientDto,
            @RequestParam(value = "changePassword", required = false) String changePassword,
            @ModelAttribute("changePasswordRequest") ChangePasswordRequest changePasswordRequest,
            HttpSession session,
            RedirectAttributes redirectAttributes
    ) {
        Client client = (Client) session.getAttribute("client");
        Long clientId = client.getId();

        boolean hasErrors = false;

        if (clientDto.getFirstName() == null || clientDto.getFirstName().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorFirstName", "Имя не может быть пустым.");
            hasErrors = true;
        }

        if (clientDto.getLastName() == null || clientDto.getLastName().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorLastName", "Фамилия не может быть пустой.");
            hasErrors = true;
        }

        if (clientDto.getPhone() == null || clientDto.getPhone().isEmpty()) {
            redirectAttributes.addFlashAttribute("errorPhone", "Номер телефона не может быть пустым.");
            hasErrors = true;
        }

        if ("on".equals(changePassword)) {
            String currentPassword = changePasswordRequest.getCurrentPassword();
            String newPassword = changePasswordRequest.getNewPassword();
            String confirmNewPassword = changePasswordRequest.getConfirmNewPassword();

            if (newPassword == null || newPassword.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorNewPassword", "Пароль не может быть пустым.");
                hasErrors = true;
            } else if (!newPassword.equals(confirmNewPassword)) {
                redirectAttributes.addFlashAttribute("errorConfirmNewPassword", "Пароли не совпадают.");
                hasErrors = true;
            }

            if (currentPassword == null || currentPassword.isEmpty()) {
                redirectAttributes.addFlashAttribute("errorCurrentPassword", "Текущий пароль обязателен для смены.");
                hasErrors = true;
            }
        }

        if (hasErrors) {
            redirectAttributes.addFlashAttribute("clientDto", clientDto);
            redirectAttributes.addFlashAttribute("changePasswordRequest", changePasswordRequest);
            return "redirect:/editProfile";
        }

        try {
            clientService.updateClientById(clientId, clientDto);

            if ("on".equals(changePassword)) {
                clientService.changePassword(
                        clientId,
                        changePasswordRequest.getCurrentPassword(),
                        changePasswordRequest.getNewPassword(),
                        changePasswordRequest.getConfirmNewPassword()
                );
            }

            Client updatedClient = clientService.findClientById(clientId);
            session.setAttribute("client", updatedClient);

            return "redirect:/clientProfile";

        } catch (ClientAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorPhone", e.getMessage());
        } catch (PasswordMismatchException e) {
            redirectAttributes.addFlashAttribute("errorCurrentPassword", e.getMessage());
        }

        redirectAttributes.addFlashAttribute("clientDto", clientDto);
        redirectAttributes.addFlashAttribute("changePasswordRequest", changePasswordRequest);
        return "redirect:/editProfile";
    }

    @PostMapping("/deleteClient")
    public String deleteClient(HttpSession session,
                               RedirectAttributes redirectAttributes,
                               HttpServletRequest request,
                               HttpServletResponse response) {
        Client client = (Client) session.getAttribute("client");

        try {
            clientService.deleteClientById(client.getId());

            Cookie jwtCookie = new Cookie("JWT", null);
            jwtCookie.setMaxAge(0);
            jwtCookie.setPath("/");
            response.addCookie(jwtCookie);

            SecurityContextHolder.clearContext();
            request.getSession().invalidate();

            return "redirect:/login?deleted";
        } catch (AppointmentsNotCancelledException e) {
            redirectAttributes.addFlashAttribute("error", "cancel_appointments_first");
            return "redirect:/clientProfile";
        }
    }

}
