package ru.fav.petcare.grooming.salon.controller.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fav.petcare.grooming.salon.controller.request.RegisterClientRequest;
import ru.fav.petcare.grooming.salon.controller.request.LoginClientRequest;
import ru.fav.petcare.grooming.salon.controller.response.JwtResponse;
import ru.fav.petcare.grooming.salon.entity.Client;
import ru.fav.petcare.grooming.salon.security.JwtTokenUtils;
import ru.fav.petcare.grooming.salon.service.ClientAuthService;

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
            @ApiResponse(responseCode = "200", description = "Успешный вход, возвращает JWT-токен",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Неверные учетные данные",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> createAuthToken(@Valid @RequestBody LoginClientRequest loginRequest) {
        Client client = clientAuthService.login(loginRequest.getPhone(), loginRequest.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getPhone());
        String token = jwtTokenUtils.generateToken(client.getId(),  userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }


    @Operation(summary = "Регистрация нового пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Успешная регистрация, возвращает JWT-токен",
                    content = @Content(schema = @Schema(implementation = JwtResponse.class))),
            @ApiResponse(responseCode = "400", description = "Некорректные данные",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Пользователь уже существует",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> createNewUser(@Valid @RequestBody RegisterClientRequest registerClientRequest) {
        Client client = clientAuthService.register(
                registerClientRequest.getFirstName(),
                registerClientRequest.getLastName(),
                registerClientRequest.getPhone(),
                registerClientRequest.getPassword(),
                registerClientRequest.getConfirmPassword()
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(client.getPhone());
        String token = jwtTokenUtils.generateToken(client.getId(),  userDetails);

        return ResponseEntity.ok(new JwtResponse(token));
    }
}
