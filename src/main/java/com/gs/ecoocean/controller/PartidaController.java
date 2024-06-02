package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.partida.PartidaCreateDTO;
import com.gs.ecoocean.dto.partida.PartidaResponseDTO;
import com.gs.ecoocean.dto.partida.PartidaUpdateDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioCreateDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioResponseDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioUpdateDTO;
import com.gs.ecoocean.model.Partida;
import com.gs.ecoocean.model.Voluntario;
import com.gs.ecoocean.service.PartidaService;
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
@RequestMapping("/partidas")
public class PartidaController {

    @Autowired
    private PartidaService service;

    @GetMapping
    public ResponseEntity<Page<PartidaResponseDTO>> index(@PageableDefault(size = 10) Pageable pageable){
        Page<PartidaResponseDTO> partidas = service.index(pageable).map(PartidaResponseDTO::new);
        return ResponseEntity.ok(partidas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PartidaResponseDTO> get(@PathVariable Long id){
        Partida partida = service.get(id);
        return ResponseEntity.ok(new PartidaResponseDTO(partida));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid PartidaCreateDTO partidaDTO){
        Partida partidaSaved = service.create(partidaDTO);
        URI uriLocation = Uri.createUriLocation(partidaSaved.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<PartidaResponseDTO> update(@PathVariable Long id, @RequestBody @Valid PartidaUpdateDTO partidaUpdateDTO){
        Partida partidaUpdated = service.update(partidaUpdateDTO,id);
        return ResponseEntity.ok(new PartidaResponseDTO(partidaUpdated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> cancelar(@PathVariable Long id){
        service.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/encerrar-ativar/{id}")
    public ResponseEntity<Void> encerrarOuIniciar(@PathVariable Long id){
        service.encerrarOuAtivar(id);
        return ResponseEntity.noContent().build();
    }
}
