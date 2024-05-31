package com.gs.ecoocean.dto.voluntario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record VoluntarioUpdateDTO(
        @NotBlank @Email String email
) {
}
