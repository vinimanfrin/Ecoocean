package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.coleta.ColetaCreateDTO;
import com.gs.ecoocean.dto.coleta.ColetaResponseDTO;
import com.gs.ecoocean.model.Coleta;
import com.gs.ecoocean.service.ColetaService;
import com.gs.ecoocean.utils.Uri;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/coletas")
public class ColetaController {

    @Autowired
    private ColetaService service;

    @GetMapping
    public ResponseEntity<Page<ColetaResponseDTO>> index(@PageableDefault(size = 10) Pageable pageable){
        Page<ColetaResponseDTO> coletas = service.index(pageable).map(ColetaResponseDTO::new);
        return ResponseEntity.ok(coletas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ColetaResponseDTO> get(@PathVariable Long id){
        Coleta coleta = service.get(id);
        return ResponseEntity.ok(new ColetaResponseDTO(coleta));
    }

    @PostMapping
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Void> create(@RequestBody @Valid ColetaCreateDTO coletaCreateDTO){
        Coleta coletaSaved = service.create(coletaCreateDTO);
        URI uriLocation = Uri.createUriLocation(coletaSaved.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
