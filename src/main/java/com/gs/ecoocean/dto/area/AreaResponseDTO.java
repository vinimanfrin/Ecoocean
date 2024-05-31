package com.gs.ecoocean.dto.area;

import com.gs.ecoocean.model.Area;

public record AreaResponseDTO(
        Long id,
        String cep,
        String cidade,
        String estado,
        String rua,
        String descricao,
        String latitudex,
        String latitudey
) {
    public AreaResponseDTO(Area area) {
        this(area.getId(), area.getCep(), area.getCidade(), area.getEstado(), area.getRua(), area.getDescricao(), area.getLatitudex(), area.getLatitudey());
    }
}
