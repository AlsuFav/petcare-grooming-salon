package ru.fav.petcare.grooming.salon.controller.web;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.fav.petcare.grooming.salon.controller.dto.ClientDto;
import ru.fav.petcare.grooming.salon.controller.request.LoginClientRequest;
import ru.fav.petcare.grooming.salon.controller.request.RegisterClientRequest;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.exception.ClientAlreadyExistsException;
import ru.fav.petcare.grooming.salon.exception.InvalidCredentialsException;
import ru.fav.petcare.grooming.salon.exception.PasswordMismatchException;
import ru.fav.petcare.grooming.salon.security.JwtTokenUtils;
import ru.fav.petcare.grooming.salon.service.ClientAuthService;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final ClientAuthService clientAuthService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @ModelAttribute("loginClientRequest") LoginClientRequest loginClientRequest,
            HttpServletRequest request,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        request.getSession().invalidate();

        String phone = loginClientRequest.getPhone();
        String password = loginClientRequest.getPassword();

        try {
            Client client = clientAuthService.login(phone, password);
            UserDetails userDetails = userDetailsService.loadUserByUsername(phone);
            String token = jwtTokenUtils.generateToken(client.getId(), userDetails);
            Cookie jwtCookie = new Cookie("JWT", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(30 * 60);

            response.addCookie(jwtCookie);

            return "redirect:/clientProfile";

        } catch (InvalidCredentialsException e) {
            redirectAttributes.addFlashAttribute("error", "invalid");
            return "redirect:/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie jwtCookie = new Cookie("JWT", null);
        jwtCookie.setMaxAge(0);
        jwtCookie.setPath("/");
        response.addCookie(jwtCookie);

        SecurityContextHolder.clearContext();
        request.getSession().invalidate();

        return "redirect:/login?logout";
    }

    @GetMapping("/register")
    public String showRegistrationPage() {
        return "registration";
    }

    @PostMapping("/register")
    public String register(
            @ModelAttribute("registerClientRequest") RegisterClientRequest registerClientRequest,
            HttpServletResponse response,
            RedirectAttributes redirectAttributes
    ) {
        String firstName = registerClientRequest.getFirstName();
        String lastName = registerClientRequest.getLastName();
        String phone = registerClientRequest.getPhone();
        String password = registerClientRequest.getPassword();
        String confirmPassword = registerClientRequest.getConfirmPassword();

        boolean hasErrors = false;

        if (firstName.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorFirstName", "Имя не может быть пустым.");
            hasErrors = true;
        }
        if (lastName.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorLastName", "Фамилия не может быть пустой.");
            hasErrors = true;
        }
        if (phone.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorPhone", "Телефон не может быть пустым.");
            hasErrors = true;
        }
        if (password.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorPassword", "Пароль не может быть пустым.");
            hasErrors = true;
        }

        if (!password.equals(confirmPassword)) {
            redirectAttributes.addFlashAttribute("errorConfirmPassword", "Пароли не совпадают.");
            hasErrors = true;
        }

        if (hasErrors) {
            redirectAttributes.addFlashAttribute("firstName", firstName);
            redirectAttributes.addFlashAttribute("lastName", lastName);
            redirectAttributes.addFlashAttribute("phone", phone);
            return "redirect:/register";
        }

        try {
            Client client = clientAuthService.register(firstName, lastName, phone, password, confirmPassword);
            UserDetails userDetails = userDetailsService.loadUserByUsername(phone);

            String token = jwtTokenUtils.generateToken(client.getId(), userDetails);
            Cookie jwtCookie = new Cookie("JWT", token);
            jwtCookie.setHttpOnly(true);
            jwtCookie.setPath("/");
            jwtCookie.setMaxAge(30 * 60);
            response.addCookie(jwtCookie);

            return "redirect:/clientProfile";
        } catch (ClientAlreadyExistsException e) {
            redirectAttributes.addFlashAttribute("errorPhone", e.getMessage());
            return "redirect:/register";
        }
    }
}
