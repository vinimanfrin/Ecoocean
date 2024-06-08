package com.gs.ecoocean.dto.voluntario;

import java.math.BigDecimal;

public record VoluntarioRankingDTO(
        Long voluntarioId,
        String nome,
        BigDecimal totalPontos
) {
}
