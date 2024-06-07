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
@RequestMapping("/areas")
@Tag(name = "areas", description = "Controller relacionado aos dados de áreas")
public class AreaController {

    @Autowired
    private AreaService service;

    @Autowired
    private PagedResourcesAssembler pagedResourcesAssembler;

    @GetMapping
    @Operation(summary = "Lista todas as áreas", description = "A listagem ocorre com paginação e tamanho padrão de 10 áreas por requisição," +
            "endpoint não requer autenticação")
    public ResponseEntity<PagedModel<EntityModel<AreaResponseDTO>>> index(@ParameterObject @PageableDefault(size = 10) Pageable pageable){
        Page<AreaResponseDTO> areas = service.index(pageable).map(AreaResponseDTO::new);
        PagedModel<EntityModel<AreaResponseDTO>> pagedModel = pagedResourcesAssembler.toModel(areas, areaResponseDTO -> {
            EntityModel<AreaResponseDTO> entityModel = EntityModel.of((AreaResponseDTO) areaResponseDTO);
            entityModel.add(linkTo(methodOn(AreaController.class).get(((AreaResponseDTO) areaResponseDTO).id())).withSelfRel());
            entityModel.add(linkTo(methodOn(AreaController.class).deleteById(((AreaResponseDTO) areaResponseDTO).id())).withRel("delete"));
            entityModel.add(linkTo(methodOn(AreaController.class).index(pageable)).withRel("contents"));
            return entityModel;
        });
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "exibe os detalhes da área de id equivalente ao informado",description = "Endpoint retorna um objeto contendo os detalhes de uma área e não requer autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Área não encontrada"),
            @ApiResponse(responseCode = "200", description = "Área detalhada com sucesso!")
    })
    public ResponseEntity<EntityModel<AreaResponseDTO>> get(@PathVariable Long id){
        Area area = service.get(id);
        AreaResponseDTO areaResponseDTO = new AreaResponseDTO(area);

        EntityModel<AreaResponseDTO> entityModel = EntityModel.of(areaResponseDTO);
        entityModel.add(linkTo(methodOn(AreaController.class).get(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(AreaController.class).deleteById(id)).withRel("delete"));
        entityModel.add(linkTo(methodOn(AreaController.class).index(Pageable.unpaged())).withRel("contents"));
        return ResponseEntity.ok(entityModel);
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
    public ResponseEntity<EntityModel<AreaResponseDTO>> create(@ParameterObject @RequestBody @Valid AreaCreateDTO areaDTO){
        Area areaSaved = service.create(areaDTO);
        AreaResponseDTO areaResponseDTO = new AreaResponseDTO(areaSaved);
        EntityModel<AreaResponseDTO> entityModel = EntityModel.of(areaResponseDTO);
        entityModel.add(linkTo(methodOn(AreaController.class).get(areaSaved.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(AreaController.class).index(Pageable.unpaged())).withRel("contents"));
        URI uriLocation = linkTo(methodOn(AreaController.class).get(areaSaved.getId())).toUri();
        return ResponseEntity.created(uriLocation).body(entityModel);
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados da área"),
            @ApiResponse(responseCode = "200", description = "Área atualizada com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Forbidden! Requer autenticação e role de admin")
    })
    @Operation(summary = "Atualiza uma nova área do sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados necessários para atualizar dados de uma área")
    public ResponseEntity<EntityModel<AreaResponseDTO>> update(@PathVariable Long id, @ParameterObject @RequestBody @Valid AreaUpdateDTO areaUpdateDTO){
        Area areaUpdated = service.update(areaUpdateDTO, id);
        AreaResponseDTO areaResponseDTO = new AreaResponseDTO(areaUpdated);
        EntityModel<AreaResponseDTO> entityModel = EntityModel.of(areaResponseDTO);
        entityModel.add(linkTo(methodOn(AreaController.class).get(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(AreaController.class).deleteById(id)).withRel("delete"));
        entityModel.add(linkTo(methodOn(AreaController.class).index(Pageable.unpaged())).withRel("contents"));
        return ResponseEntity.ok(entityModel);
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
