package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.coleta.ColetaCreateDTO;
import com.gs.ecoocean.dto.coleta.ColetaResponseDTO;
import com.gs.ecoocean.model.Coleta;
import com.gs.ecoocean.service.ColetaService;
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
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/coletas")
@Tag(name = "Controller relacionado aos dados de coletas")
public class ColetaController {

    @Autowired
    private ColetaService service;

    @Autowired
    private PagedResourcesAssembler<ColetaResponseDTO> pagedResourcesAssembler;

    @GetMapping
    @Operation(summary = "Lista todas as coletas", description = "A listagem ocorre com paginação e tamanho padrão de 10 coletas por requisição," +
            "requer authenticação")
    public ResponseEntity<PagedModel<EntityModel<ColetaResponseDTO>>> index(@ParameterObject @PageableDefault(size = 10) Pageable pageable){
        Page<ColetaResponseDTO> coletas = service.index(pageable).map(ColetaResponseDTO::new);
        PagedModel<EntityModel<ColetaResponseDTO>> pagedModel = pagedResourcesAssembler.toModel(coletas, coletaResponseDTO -> {
            EntityModel<ColetaResponseDTO> entityModel = EntityModel.of(coletaResponseDTO);
            entityModel.add(linkTo(methodOn(ColetaController.class).get(coletaResponseDTO.id())).withSelfRel());
            entityModel.add(linkTo(methodOn(ColetaController.class).deleteById(coletaResponseDTO.id())).withRel("delete"));
            entityModel.add(linkTo(methodOn(ColetaController.class).index(pageable)).withRel("contents"));
            return entityModel;
        });
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "exibe os detalhes da coleta de id equivalente ao informado",description = "Endpoint retorna um objeto contendo os detalhes de uma coleta e requer autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Coleta não encontrada"),
            @ApiResponse(responseCode = "200", description = "Coleta detalhada com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Não autorizado, requer authenticação")
    })
    public ResponseEntity<EntityModel<ColetaResponseDTO>> get(@PathVariable Long id){
        Coleta coleta = service.get(id);
        ColetaResponseDTO coletaResponseDTO = new ColetaResponseDTO(coleta);
        EntityModel<ColetaResponseDTO> entityModel = EntityModel.of(coletaResponseDTO);
        entityModel.add(linkTo(methodOn(ColetaController.class).get(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(ColetaController.class).deleteById(id)).withRel("delete"));
        entityModel.add(linkTo(methodOn(ColetaController.class).index(Pageable.unpaged())).withRel("contents"));
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    @PreAuthorize(value = "hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados da coleta"),
            @ApiResponse(responseCode = "201", description = "Coleta cadastrada com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Forbidden! Requer autenticação e role de admin")
    })
    @Operation(summary = "Cadastra uma nova Coleta no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados" +
            " necessários para realizar uma nova coleta")
    public ResponseEntity<EntityModel<ColetaResponseDTO>> create(@ParameterObject @RequestBody @Valid ColetaCreateDTO coletaCreateDTO){
        Coleta coletaSaved = service.create(coletaCreateDTO);
        ColetaResponseDTO coletaResponseDTO = new ColetaResponseDTO(coletaSaved);
        EntityModel<ColetaResponseDTO> entityModel = EntityModel.of(coletaResponseDTO);
        entityModel.add(linkTo(methodOn(ColetaController.class).get(coletaSaved.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(ColetaController.class).index(Pageable.unpaged())).withRel("contents"));
        URI uriLocation = linkTo(methodOn(ColetaController.class).get(coletaSaved.getId())).toUri();
        return ResponseEntity.created(uriLocation).body(entityModel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Coleta não encontrada"),
            @ApiResponse(responseCode = "204", description = "Coleta removida com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Forbidden! Requer autenticação e role de admin")
    })
    @Operation(summary = "Deleta uma coleta do sistema", description = "Endpoint recebe no path o id da coleta a ser deletada")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
