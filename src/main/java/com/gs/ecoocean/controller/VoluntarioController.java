package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.voluntario.VoluntarioCreateDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioResponseDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioUpdateDTO;
import com.gs.ecoocean.model.Voluntario;
import com.gs.ecoocean.service.VoluntarioService;
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
@RequestMapping("/voluntarios")
@Tag(name = "voluntários", description = "Controller relacionado aos dados de voluntários")
public class VoluntarioController {

    @Autowired
    private VoluntarioService service;

    @Autowired
    private PagedResourcesAssembler<VoluntarioResponseDTO> pagedResourcesAssembler;

    @GetMapping
    @Operation(summary = "Lista todos os voluntários", description = "A listagem ocorre com paginação e tamanho padrão de 10 voluntários por requisição," +
            "endpoint requer autenticação")
    public ResponseEntity<PagedModel<EntityModel<VoluntarioResponseDTO>>> index(@ParameterObject @PageableDefault(size = 10) Pageable pageable){
        Page<VoluntarioResponseDTO> voluntarios = service.index(pageable).map(VoluntarioResponseDTO::new);
        PagedModel<EntityModel<VoluntarioResponseDTO>> pagedModel = pagedResourcesAssembler.toModel(voluntarios, voluntarioResponseDTO -> {
            EntityModel<VoluntarioResponseDTO> entityModel = EntityModel.of(voluntarioResponseDTO);
            entityModel.add(linkTo(methodOn(VoluntarioController.class).get(voluntarioResponseDTO.id())).withSelfRel());
            entityModel.add(linkTo(methodOn(VoluntarioController.class).deleteById(voluntarioResponseDTO.id())).withRel("delete"));
            entityModel.add(linkTo(methodOn(VoluntarioController.class).index(pageable)).withRel("contents"));
            return entityModel;
        });
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "exibe os detalhes do voluntário de id equivalente ao informado",description = "Endpoint retorna um objeto contendo os detalhes de um voluntário e requer autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado"),
            @ApiResponse(responseCode = "200", description = "Voluntário detalhado com sucesso!"),
            @ApiResponse(responseCode = "401|403", description = "Não autorizado! Requer autenticação")
    })
    public ResponseEntity<EntityModel<VoluntarioResponseDTO>> get(@PathVariable Long id){
        Voluntario voluntario = service.get(id);
        VoluntarioResponseDTO voluntarioResponseDTO = new VoluntarioResponseDTO(voluntario);
        EntityModel<VoluntarioResponseDTO> entityModel = EntityModel.of(voluntarioResponseDTO);
        entityModel.add(linkTo(methodOn(VoluntarioController.class).get(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(VoluntarioController.class).deleteById(id)).withRel("delete"));
        entityModel.add(linkTo(methodOn(VoluntarioController.class).index(Pageable.unpaged())).withRel("contents"));
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do voluntário"),
            @ApiResponse(responseCode = "201", description = "Voluntário cadastrado com sucesso!"),
            @ApiResponse(responseCode = "401|403", description = "Não authorizado! Requer autenticação")
    })
    @Operation(summary = "Cadastra um novo Voluntário no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados" +
            " necessários para realizar o cadastro de um novo voluntário")
    public ResponseEntity<EntityModel<VoluntarioResponseDTO>> create(@ParameterObject @RequestBody @Valid VoluntarioCreateDTO voluntarioDTO){
        Voluntario voluntarioSaved = service.create(voluntarioDTO);
        VoluntarioResponseDTO voluntarioResponseDTO = new VoluntarioResponseDTO(voluntarioSaved);
        EntityModel<VoluntarioResponseDTO> entityModel = EntityModel.of(voluntarioResponseDTO);
        entityModel.add(linkTo(methodOn(VoluntarioController.class).get(voluntarioSaved.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(VoluntarioController.class).index(Pageable.unpaged())).withRel("contents"));
        URI uriLocation = linkTo(methodOn(VoluntarioController.class).get(voluntarioSaved.getId())).toUri();
        return ResponseEntity.created(uriLocation).body(entityModel);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do voluntário"),
            @ApiResponse(responseCode = "201", description = "Voluntário atualizado com sucesso!"),
            @ApiResponse(responseCode = "401|403", description = "Não authorizado! Requer autenticação")
    })
    @Operation(summary = "Atualiza um Voluntário no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados" +
            " necessários para atualizar um voluntário")
    public ResponseEntity<EntityModel<VoluntarioResponseDTO>> update(@PathVariable Long id, @ParameterObject @RequestBody @Valid VoluntarioUpdateDTO voluntarioUpdateDTO){
        Voluntario voluntarioUpdated = service.update(voluntarioUpdateDTO, id);
        VoluntarioResponseDTO voluntarioResponseDTO = new VoluntarioResponseDTO(voluntarioUpdated);
        EntityModel<VoluntarioResponseDTO> entityModel = EntityModel.of(voluntarioResponseDTO);
        entityModel.add(linkTo(methodOn(VoluntarioController.class).get(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(VoluntarioController.class).index(Pageable.unpaged())).withRel("contents"));
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado"),
            @ApiResponse(responseCode = "400", description = "Erro de validação"),
            @ApiResponse(responseCode = "204", description = "Voluntário removido com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Não Autorizado! Requer autenticação")
    })
    @Operation(summary = "Deleta um Voluntário do sistema", description = "Endpoint recebe no path o id do voluntário a ser deletado")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
