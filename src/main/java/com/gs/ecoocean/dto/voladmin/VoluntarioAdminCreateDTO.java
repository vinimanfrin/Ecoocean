package com.gs.ecoocean.dto.voladmin;

import jakarta.validation.constraints.NotBlank;

public record VoluntarioAdminCreateDTO(
        @NotBlank String nome,
        @NotBlank String email,
        @NotBlank String username,
        @NotBlank String password
) {
}
