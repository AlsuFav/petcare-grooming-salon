package ru.fav.petcare.grooming.salon.controller.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FaqResponse {
    @NotNull
    private Long id;

    @NotNull
    private String question;

    @NotBlank
    private String answer;
}
