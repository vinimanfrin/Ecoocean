package com.gs.ecoocean.dto.voluntario;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record VoluntarioCreateDTO(
        @NotBlank String nome,
        @Past @NotNull @JsonFormat(pattern = "dd-MM-yyyy") LocalDate dataNascimento,
        @NotBlank @Email String email,
        @NotNull Integer sexo,
        @NotBlank String username,
        @NotBlank String password
        ) {
}
