package com.gs.ecoocean.dto.voladmin;

import com.gs.ecoocean.model.VoluntarioAdmin;

public record VoluntarioAdminRespondeDTO(
        Long id,
        String nome,
        String email
) {
    public VoluntarioAdminRespondeDTO(VoluntarioAdmin voluntarioAdmin){
        this(voluntarioAdmin.getId(), voluntarioAdmin.getNome(), voluntarioAdmin.getEmail());
    }
}
