package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.area.AreaCreateDTO;
import com.gs.ecoocean.dto.area.AreaResponseDTO;
import com.gs.ecoocean.dto.area.AreaUpdateDTO;
import com.gs.ecoocean.model.Area;
import com.gs.ecoocean.service.AreaService;
import com.gs.ecoocean.utils.Uri;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/areas")
public class AreaController {

    @Autowired
    private AreaService service;

    @GetMapping
    public ResponseEntity<Page<AreaResponseDTO>> index(@PageableDefault(size = 10) Pageable pageable){
        Page<AreaResponseDTO> areas = service.index(pageable).map(AreaResponseDTO::new);
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AreaResponseDTO> get(@PathVariable Long id){
        Area area = service.get(id);
        return ResponseEntity.ok(new AreaResponseDTO(area));
    }

    @PostMapping
    public ResponseEntity<AreaResponseDTO> create(@RequestBody @Valid AreaCreateDTO areaDTO){
        Area areaSaved = service.create(areaDTO);
        URI uriLocation = Uri.createUriLocation(areaSaved.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AreaResponseDTO> update(@PathVariable Long id, @RequestBody @Valid AreaUpdateDTO areaUpdateDTO){
        Area areaUpdated = service.update(areaUpdateDTO,id);
        return ResponseEntity.ok(new AreaResponseDTO(areaUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
