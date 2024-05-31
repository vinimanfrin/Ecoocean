package com.gs.ecoocean.dto.area;

import jakarta.validation.constraints.NotBlank;

public record AreaCreateDTO(
        @NotBlank String cep,
        @NotBlank String cidade,
        @NotBlank String estado,
        @NotBlank String rua,
        String descricao,
        @NotBlank String latitudex,
        @NotBlank String latitudey
) {
}
