package com.gs.ecoocean.dto.coleta;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ColetaCreateDTO(
        @NotNull Integer tipoLixo,
        @NotNull @Positive Integer quantidade,
        @NotNull Long idParticipacao
) {
}
