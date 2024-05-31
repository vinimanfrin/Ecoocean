package com.gs.ecoocean.dto.voluntario;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gs.ecoocean.model.Voluntario;
import com.gs.ecoocean.model.enuns.Sexo;

import java.time.LocalDate;

public record VoluntarioResponseDTO(
        Long id,
        String nome,
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDate dataNascimento,
        String email,
        Sexo sexo
) {
    public VoluntarioResponseDTO(Voluntario voluntario){
        this(voluntario.getId(), voluntario.getNome(), voluntario.getDataNascimento(), voluntario.getEmail(), voluntario.getSexo());
    }
}

