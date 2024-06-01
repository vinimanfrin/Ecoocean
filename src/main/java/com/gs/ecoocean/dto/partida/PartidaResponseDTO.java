package com.gs.ecoocean.dto.partida;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gs.ecoocean.dto.area.AreaResponseDTO;
import com.gs.ecoocean.dto.voladmin.VoluntarioAdminRespondeDTO;
import com.gs.ecoocean.model.Partida;
import com.gs.ecoocean.model.enuns.StatusPartida;

import java.time.LocalDateTime;

public record PartidaResponseDTO(
        Long id,
        String nome,
        String descricao,
        @JsonFormat(pattern = "dd/MM/yyyy - HH:mm") LocalDateTime dataInicio,
        @JsonFormat(pattern = "dd/MM/yyyy - HH:mm") LocalDateTime dataFim,
        StatusPartida status,
        AreaResponseDTO area,
        VoluntarioAdminRespondeDTO voluntarioAdmin
) {
    public PartidaResponseDTO(Partida partida){
        this(partida.getId(), partida.getNome(), partida.getDescricao(), partida.getDataInicio(),
                partida.getDataFim(),partida.getStatus(),new AreaResponseDTO(partida.getArea()),new VoluntarioAdminRespondeDTO(partida.getVoluntarioAdmin()));
    }
}
