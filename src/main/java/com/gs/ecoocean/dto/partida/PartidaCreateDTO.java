package com.gs.ecoocean.dto.partida;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PartidaCreateDTO(
        @NotBlank String nome,
        @NotBlank String descricao,
        @NotNull Long areaId,
        @NotNull Long voluntarioAdminId
        ) {
}
