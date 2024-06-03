package com.gs.ecoocean.dto.coleta;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ColetaCreateDTO(
        @NotNull Integer tipoLixo,
        @NotNull @Positive BigDecimal quantidade,
        @NotNull Long idParticipacao
) {
}
