package com.gs.ecoocean.dto.participacao;

import java.math.BigDecimal;

import com.gs.ecoocean.dto.partida.PartidaResponseDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioResponseDTO;
import com.gs.ecoocean.model.Participacao;

public record ParticipacaoResponseDTO(
    VoluntarioResponseDTO voluntario,
    PartidaResponseDTO partida,
    BigDecimal pontuacao
) {

    public ParticipacaoResponseDTO(Participacao participacao){
        this(new VoluntarioResponseDTO(participacao.getVoluntario()),new PartidaResponseDTO(participacao.getPartida()), participacao.getPontuacao());
    }
}
