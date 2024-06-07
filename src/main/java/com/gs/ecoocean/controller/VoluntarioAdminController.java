package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.voladmin.VoluntarioAdminCreateDTO;
import com.gs.ecoocean.dto.voladmin.VoluntarioAdminRespondeDTO;
import com.gs.ecoocean.dto.voladmin.VoluntarioAdminUpdateDTO;
import com.gs.ecoocean.model.VoluntarioAdmin;
import com.gs.ecoocean.service.VoluntarioAdminService;
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
@RequestMapping("/voluntarios-admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "voluntarios-admin", description = "Controller relacionado aos dados dos voluntários administradores")
public class VoluntarioAdminController {

    @Autowired
    private VoluntarioAdminService service;

    @Autowired
    private PagedResourcesAssembler<VoluntarioAdminRespondeDTO> pagedResourcesAssembler;

    @GetMapping
    @Operation(summary = "Lista todas os voluntários-admin", description = "A listagem ocorre com paginação e tamanho padrão de 10 voluntários-admin por requisição," +
            "endpoint requer autenticação e perfil de admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Exibindo todos os voluntarios administradores"),
            @ApiResponse(responseCode = "403|401", description = "Não autorizado! Requer autenticação e role de admin")
    })
    public ResponseEntity<PagedModel<EntityModel<VoluntarioAdminRespondeDTO>>> index(@ParameterObject @PageableDefault(size = 10) Pageable pageable){
        Page<VoluntarioAdminRespondeDTO> voluntariosAdmin = service.index(pageable).map(VoluntarioAdminRespondeDTO::new);
        PagedModel<EntityModel<VoluntarioAdminRespondeDTO>> pagedModel = pagedResourcesAssembler.toModel(voluntariosAdmin, voluntarioAdminRespondeDTO -> {
            EntityModel<VoluntarioAdminRespondeDTO> entityModel = EntityModel.of(voluntarioAdminRespondeDTO);
            entityModel.add(linkTo(methodOn(VoluntarioAdminController.class).get(voluntarioAdminRespondeDTO.id())).withSelfRel());
            entityModel.add(linkTo(methodOn(VoluntarioAdminController.class).deleteById(voluntarioAdminRespondeDTO.id())).withRel("delete"));
            entityModel.add(linkTo(methodOn(VoluntarioAdminController.class).index(pageable)).withRel("contents"));
            return entityModel;
        });
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "exibe os detalhes do admin de id equivalente ao informado",description = "Endpoint retorna um objeto contendo os detalhes de um voluntario-admin, requer autenticação e perfil de admin")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Admin não encontrado"),
            @ApiResponse(responseCode = "200", description = "Admin detalhado com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Não autorizado! Requer autenticação e role de admin")
    })
    public ResponseEntity<EntityModel<VoluntarioAdminRespondeDTO>> get(@PathVariable Long id){
        VoluntarioAdmin voluntarioAdmin = service.get(id);
        VoluntarioAdminRespondeDTO voluntarioAdminRespondeDTO = new VoluntarioAdminRespondeDTO(voluntarioAdmin);
        EntityModel<VoluntarioAdminRespondeDTO> entityModel = EntityModel.of(voluntarioAdminRespondeDTO);
        entityModel.add(linkTo(methodOn(VoluntarioAdminController.class).get(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(VoluntarioAdminController.class).deleteById(id)).withRel("delete"));
        entityModel.add(linkTo(methodOn(VoluntarioAdminController.class).index(Pageable.unpaged())).withRel("contents"));
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do voluntario admin"),
            @ApiResponse(responseCode = "201", description = "Voluntário admin cadastrado com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Não Autorizado! Requer autenticação e role de admin")
    })
    @Operation(summary = "Cadastra um novo Voluntario admin no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados" +
            " necessários para realizar o cadastro de um novo administrador")
    public ResponseEntity<EntityModel<VoluntarioAdminRespondeDTO>> create(@ParameterObject @RequestBody @Valid VoluntarioAdminCreateDTO voluntarioAdminDTO){
        VoluntarioAdmin voluntarioAdminSaved = service.create(voluntarioAdminDTO);
        VoluntarioAdminRespondeDTO voluntarioAdminRespondeDTO = new VoluntarioAdminRespondeDTO(voluntarioAdminSaved);
        EntityModel<VoluntarioAdminRespondeDTO> entityModel = EntityModel.of(voluntarioAdminRespondeDTO);
        entityModel.add(linkTo(methodOn(VoluntarioAdminController.class).get(voluntarioAdminSaved.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(VoluntarioAdminController.class).index(Pageable.unpaged())).withRel("contents"));
        URI uriLocation = linkTo(methodOn(VoluntarioAdminController.class).get(voluntarioAdminSaved.getId())).toUri();
        return ResponseEntity.created(uriLocation).body(entityModel);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do voluntario admin"),
            @ApiResponse(responseCode = "201", description = "Voluntário admin atualizado com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Não Autorizado! Requer autenticação e role de admin")
    })
    @Operation(summary = "Atualiza os dados de um Voluntario admin no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados" +
            " necessários para realizar a atualização de um administrador")
    public ResponseEntity<EntityModel<VoluntarioAdminRespondeDTO>> update(@PathVariable Long id, @ParameterObject @RequestBody @Valid VoluntarioAdminUpdateDTO voluntarioAdminUpdateDTO){
        VoluntarioAdmin voluntarioAdminUpdated = service.update(voluntarioAdminUpdateDTO, id);
        VoluntarioAdminRespondeDTO voluntarioAdminRespondeDTO = new VoluntarioAdminRespondeDTO(voluntarioAdminUpdated);
        EntityModel<VoluntarioAdminRespondeDTO> entityModel = EntityModel.of(voluntarioAdminRespondeDTO);
        entityModel.add(linkTo(methodOn(VoluntarioAdminController.class).get(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(VoluntarioAdminController.class).index(Pageable.unpaged())).withRel("contents"));
        return ResponseEntity.ok(entityModel);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Administrador não encontrado"),
            @ApiResponse(responseCode = "204", description = "Voluntário admin removido com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Não Autorizado! Requer autenticação e role de admin")
    })
    @Operation(summary = "Remove um Voluntario admin no sistema", description = "Endpoint recebe no path da requisição o id de um admin a ser removido")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
