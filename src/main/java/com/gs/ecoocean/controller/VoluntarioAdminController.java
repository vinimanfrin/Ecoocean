package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.voladmin.VoluntarioAdminCreateDTO;
import com.gs.ecoocean.dto.voladmin.VoluntarioAdminRespondeDTO;
import com.gs.ecoocean.dto.voladmin.VoluntarioAdminUpdateDTO;
import com.gs.ecoocean.model.VoluntarioAdmin;
import com.gs.ecoocean.service.VoluntarioAdminService;
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
@RequestMapping("/voluntarios-admin")
@PreAuthorize("hasRole('ADMIN')")
public class VoluntarioAdminController {

    @Autowired
    private VoluntarioAdminService service;

    @GetMapping
    public ResponseEntity<Page<VoluntarioAdminRespondeDTO>> index(@PageableDefault(size = 10) Pageable pageable){
        Page<VoluntarioAdminRespondeDTO> voluntariosAdmin = service.index(pageable).map(VoluntarioAdminRespondeDTO::new);
        return ResponseEntity.ok(voluntariosAdmin);
    }

    @GetMapping("/{id}")
    public ResponseEntity<VoluntarioAdminRespondeDTO> get(@PathVariable Long id){
        VoluntarioAdmin voluntarioAdmin = service.get(id);
        return ResponseEntity.ok(new VoluntarioAdminRespondeDTO(voluntarioAdmin));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid VoluntarioAdminCreateDTO voluntarioAdminDTO){
        VoluntarioAdmin voluntarioAdminSaved = service.create(voluntarioAdminDTO);
        URI uriLocation = Uri.createUriLocation(voluntarioAdminSaved.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<VoluntarioAdminRespondeDTO> update(@PathVariable Long id, @RequestBody @Valid VoluntarioAdminUpdateDTO voluntarioAdminUpdateDTO){
        VoluntarioAdmin voluntarioAdminUpdated = service.update(voluntarioAdminUpdateDTO,id);
        return ResponseEntity.ok(new VoluntarioAdminRespondeDTO(voluntarioAdminUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
