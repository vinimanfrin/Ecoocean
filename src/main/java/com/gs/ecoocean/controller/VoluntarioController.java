package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.voluntario.VoluntarioCreateDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioResponseDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioUpdateDTO;
import com.gs.ecoocean.model.Voluntario;
import com.gs.ecoocean.service.VoluntarioService;
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
@RequestMapping("/voluntarios")
public class VoluntarioController {

    @Autowired
    private VoluntarioService service;

    @GetMapping
    public ResponseEntity<Page<VoluntarioResponseDTO>> index(@PageableDefault(size = 10) Pageable pageable){
        Page<VoluntarioResponseDTO> voluntarios = service.index(pageable).map(VoluntarioResponseDTO::new);
        return ResponseEntity.ok(voluntarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoluntarioResponseDTO> get(@PathVariable Long id){
        Voluntario voluntario = service.get(id);
        return ResponseEntity.ok(new VoluntarioResponseDTO(voluntario));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid VoluntarioCreateDTO voluntarioDTO){
        Voluntario voluntarioSaved = service.create(voluntarioDTO);
        URI uriLocation = Uri.createUriLocation(voluntarioSaved.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoluntarioResponseDTO> update(@PathVariable Long id, @RequestBody @Valid VoluntarioUpdateDTO voluntarioUpdateDTO){
        Voluntario voluntarioUpdated = service.update(voluntarioUpdateDTO,id);
        return ResponseEntity.ok(new VoluntarioResponseDTO(voluntarioUpdated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
