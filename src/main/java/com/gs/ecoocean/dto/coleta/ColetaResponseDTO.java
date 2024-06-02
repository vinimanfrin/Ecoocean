package com.gs.ecoocean.dto.coleta;

import com.gs.ecoocean.dto.participacao.ParticipacaoResponseDTO;
import com.gs.ecoocean.model.Coleta;
import com.gs.ecoocean.model.enuns.TipoLixo;

public record ColetaResponseDTO(
        Long id,
        TipoLixo tipoLixo,
        Integer quantidade,
        ParticipacaoResponseDTO participacao
) {
    public ColetaResponseDTO(Coleta coleta){
        this(coleta.getId(), coleta.getTipoLixo(), coleta.getQuantidade(), new ParticipacaoResponseDTO(coleta.getParticipacao()));
    }
}
