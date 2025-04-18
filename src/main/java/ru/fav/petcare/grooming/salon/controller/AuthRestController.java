package ru.fav.petcare.grooming.salon.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fav.petcare.grooming.salon.controller.dto.RegistrationClientDto;
import ru.fav.petcare.grooming.salon.controller.request.LoginClientRequest;
import ru.fav.petcare.grooming.salon.controller.response.JwtResponse;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.exception.InvalidCredentialsException;
import ru.fav.petcare.grooming.salon.security.JwtTokenUtils;
import ru.fav.petcare.grooming.salon.service.ClientAuthService;
import ru.fav.petcare.grooming.salon.service.ClientService;

@Tag(name = "Auth Controller", description = "Вход и регистрация")
@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AuthRestController {
    private final ClientAuthService clientAuthService;
    private final UserDetailsService userDetailsService;
    private final JwtTokenUtils jwtTokenUtils;

    @Operation(summary = "Аутентификация пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешный вход, возвращает JWT-токен"),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные")
    })

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createAuthToken(@RequestBody LoginClientRequest loginRequest) {
        Client client = clientAuthService.login(loginRequest.getPhone(), loginRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getPhone());
        String token = jwtTokenUtils.generateToken(client.getId(),  userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }


    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешная регистрация, возвращает JWT-токен"),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Пользователь уже существует")
    })

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> createNewUser(@Valid @RequestBody RegistrationClientDto registrationClientDto) {
        Client client = clientAuthService.register(
                registrationClientDto.getFirstName(),
                registrationClientDto.getLastName(),
                registrationClientDto.getPhone(),
                registrationClientDto.getPassword(),
                registrationClientDto.getConfirmPassword()
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(client.getPhone());
        String token = jwtTokenUtils.generateToken(client.getId(),  userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
