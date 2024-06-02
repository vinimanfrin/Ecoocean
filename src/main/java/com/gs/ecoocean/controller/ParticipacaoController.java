package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.participacao.ParticipacaoCreateDTO;
import com.gs.ecoocean.dto.participacao.ParticipacaoResponseDTO;
import com.gs.ecoocean.model.Participacao;
import com.gs.ecoocean.service.ParticipacaoService;
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
@RequestMapping("/participacoes")
public class ParticipacaoController {

    @Autowired
    private ParticipacaoService service;

    @GetMapping
    public ResponseEntity<Page<ParticipacaoResponseDTO>> index(@PageableDefault(size = 10) Pageable pageable){
        Page<ParticipacaoResponseDTO> participacoes = service.index(pageable).map(ParticipacaoResponseDTO::new);
        return ResponseEntity.ok(participacoes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipacaoResponseDTO> get(@PathVariable Long id){
        Participacao participacao = service.get(id);
        return ResponseEntity.ok(new ParticipacaoResponseDTO(participacao));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody @Valid ParticipacaoCreateDTO participacaoCreateDTO){
        Participacao participacaoSaved = service.create(participacaoCreateDTO);
        URI uriLocation = Uri.createUriLocation(participacaoSaved.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
