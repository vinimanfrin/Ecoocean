package com.gs.ecoocean.dto.voladmin;

import jakarta.validation.constraints.NotBlank;

public record VoluntarioAdminUpdateDTO(
        @NotBlank String nome,
        @NotBlank String email
) {
}
