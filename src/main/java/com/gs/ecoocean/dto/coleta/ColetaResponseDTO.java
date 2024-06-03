package com.gs.ecoocean.dto.coleta;

import java.math.BigDecimal;

import com.gs.ecoocean.dto.participacao.ParticipacaoResponseDTO;
import com.gs.ecoocean.model.Coleta;
import com.gs.ecoocean.model.enuns.TipoLixo;

public record ColetaResponseDTO(
        Long id,
        TipoLixo tipoLixo,
        BigDecimal quantidade,
        BigDecimal pontuacao,
        ParticipacaoResponseDTO participacao
) {
    public ColetaResponseDTO(Coleta coleta){
        this(coleta.getId(), coleta.getTipoLixo(), coleta.getQuantidade(),coleta.getPontuacao(), new ParticipacaoResponseDTO(coleta.getParticipacao()));
    }
}
