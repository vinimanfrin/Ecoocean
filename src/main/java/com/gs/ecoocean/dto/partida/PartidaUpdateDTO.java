package com.gs.ecoocean.dto.partida;

import jakarta.validation.constraints.NotBlank;

public record PartidaUpdateDTO(
        @NotBlank String nome,
        @NotBlank String descricao
) {
}
