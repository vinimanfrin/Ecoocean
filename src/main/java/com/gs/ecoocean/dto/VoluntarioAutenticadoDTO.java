package com.gs.ecoocean.dto;

import com.gs.ecoocean.model.Auth;

public record VoluntarioAutenticadoDTO(
        Long id,
        String nome,
        String email,
        Auth auth
) {
}
