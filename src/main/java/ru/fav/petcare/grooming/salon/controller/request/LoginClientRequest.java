package ru.fav.petcare.grooming.salon.controller.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginClientRequest {
    @Pattern(
            regexp = "\\+7 \\(\\d{3}\\) \\d{3}-\\d{2}-\\d{2}",
            message = "Phone number must match the format +7 (XXX) XXX-XX-XX"
    )
    private String phone;

    @NotBlank
    private String password;
}