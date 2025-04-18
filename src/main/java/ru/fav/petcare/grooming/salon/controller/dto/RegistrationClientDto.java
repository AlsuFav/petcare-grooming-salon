package ru.fav.petcare.grooming.salon.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationClientDto {

    @NotBlank
    @Size(max = 50)
    private String firstName;

    @NotBlank
    @Size(max = 50)
    private String lastName;

    @NotBlank
    private String password;

    @NotBlank
    private String confirmPassword;

    @NotBlank
    @Pattern(
        regexp = "\\+7 \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}",
        message = "Номер телефона должен быть в формате +7 (XXX) XXX-XX-XX"
    )
    private String phone;
}
