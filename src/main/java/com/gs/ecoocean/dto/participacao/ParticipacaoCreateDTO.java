package com.gs.ecoocean.dto.participacao;

import jakarta.validation.constraints.NotNull;

public record ParticipacaoCreateDTO(
        @NotNull Long idVoluntario,
        @NotNull Long idPartida
) {
}
