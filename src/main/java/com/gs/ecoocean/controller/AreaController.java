package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.area.AreaCreateDTO;
import com.gs.ecoocean.dto.area.AreaResponseDTO;
import com.gs.ecoocean.dto.area.AreaUpdateDTO;
import com.gs.ecoocean.model.Area;
import com.gs.ecoocean.service.AreaService;
import com.gs.ecoocean.utils.Uri;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;

@RestController
@RequestMapping("/areas")
@Tag(name = "areas", description = "Controller relacionado aos dados de áreas")
public class AreaController {

    @Autowired
    private AreaService service;

    @GetMapping
    @Operation(summary = "Lista todas as áreas", description = "A listagem ocorre com paginação e tamanho padrão de 10 áreas por requisição," +
            "endpoint não requer autenticação")
    public ResponseEntity<Page<AreaResponseDTO>> index(@ParameterObject @PageableDefault(size = 10) Pageable pageable){
        Page<AreaResponseDTO> areas = service.index(pageable).map(AreaResponseDTO::new);
        return ResponseEntity.ok(areas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "exibe os detalhes da área de id equivalente ao informado",description = "Endpoint retorna um objeto contendo os detalhes de uma área e não requer autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Área não encontrada"),
            @ApiResponse(responseCode = "200", description = "Área detalhada com sucesso!")
    })
    public ResponseEntity<AreaResponseDTO> get(@PathVariable Long id){
        Area area = service.get(id);
        return ResponseEntity.ok(new AreaResponseDTO(area));
    }

    @PostMapping
    @PreAuthorize(value = "hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados da área"),
            @ApiResponse(responseCode = "201", description = "Área cadastrada com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Forbidden! Requer autenticação e role de admin")
    })
    @Operation(summary = "Cadastra uma nova Área no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados" +
            " necessários para realizar o cadastro de uma nova área")
    public ResponseEntity<Void> create(@ParameterObject @RequestBody @Valid AreaCreateDTO areaDTO){
        Area areaSaved = service.create(areaDTO);
        URI uriLocation = Uri.createUriLocation(areaSaved.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados da área"),
            @ApiResponse(responseCode = "200", description = "Área atualizada com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Forbidden! Requer autenticação e role de admin")
    })
    @Operation(summary = "Atualiza uma nova área do sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados necessários para atualizar dados de uma área")
    public ResponseEntity<AreaResponseDTO> update(@PathVariable Long id, @ParameterObject @RequestBody @Valid AreaUpdateDTO areaUpdateDTO){
        Area areaUpdated = service.update(areaUpdateDTO,id);
        return ResponseEntity.ok(new AreaResponseDTO(areaUpdated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Área não encontrada"),
            @ApiResponse(responseCode = "204", description = "Área removida com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Forbidden! Requer autenticação e role de admin")
    })
    @Operation(summary = "Deleta uma área do sistema", description = "Endpoint recebe no path o id da área a ser deletada")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
