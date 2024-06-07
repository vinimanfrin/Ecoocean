package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.participacao.ParticipacaoCreateDTO;
import com.gs.ecoocean.dto.participacao.ParticipacaoResponseDTO;
import com.gs.ecoocean.model.Participacao;
import com.gs.ecoocean.service.ParticipacaoService;
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
import org.springframework.web.bind.annotation.*;

import java.net.URI;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/participacoes")
@Tag(name = "participações", description = "Controller relacionado aos dados de participações")
public class ParticipacaoController {

    @Autowired
    private ParticipacaoService service;

    @Autowired
    private PagedResourcesAssembler<ParticipacaoResponseDTO> pagedResourcesAssembler;

    @GetMapping
    @Operation(summary = "Lista todas as participações", description = "A listagem ocorre com paginação e tamanho padrão de 10 participações por requisição," +
            "requer autenticação")
    public ResponseEntity<PagedModel<EntityModel<ParticipacaoResponseDTO>>> index(@ParameterObject @PageableDefault(size = 10) Pageable pageable){
        Page<ParticipacaoResponseDTO> participacoes = service.index(pageable).map(ParticipacaoResponseDTO::new);
        PagedModel<EntityModel<ParticipacaoResponseDTO>> pagedModel = pagedResourcesAssembler.toModel(participacoes, participacaoResponseDTO -> {
            EntityModel<ParticipacaoResponseDTO> entityModel = EntityModel.of(participacaoResponseDTO);
            entityModel.add(linkTo(methodOn(ParticipacaoController.class).get(participacaoResponseDTO.id())).withSelfRel());
            entityModel.add(linkTo(methodOn(ParticipacaoController.class).deleteById(participacaoResponseDTO.id())).withRel("delete"));
            entityModel.add(linkTo(methodOn(ParticipacaoController.class).index(pageable)).withRel("contents"));
            return entityModel;
        });
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{id}")
    @Operation(summary = "exibe os detalhes da participação de id equivalente ao informado",description = "Endpoint retorna um objeto contendo os detalhes de uma participação e requer autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Participação não encontrada"),
            @ApiResponse(responseCode = "200", description = "Participação detalhada com sucesso!")
    })
    public ResponseEntity<EntityModel<ParticipacaoResponseDTO>> get(@PathVariable Long id){
        Participacao participacao = service.get(id);
        ParticipacaoResponseDTO participacaoResponseDTO = new ParticipacaoResponseDTO(participacao);
        EntityModel<ParticipacaoResponseDTO> entityModel = EntityModel.of(participacaoResponseDTO);
        entityModel.add(linkTo(methodOn(ParticipacaoController.class).get(id)).withSelfRel());
        entityModel.add(linkTo(methodOn(ParticipacaoController.class).deleteById(id)).withRel("delete"));
        entityModel.add(linkTo(methodOn(ParticipacaoController.class).index(Pageable.unpaged())).withRel("contents"));
        return ResponseEntity.ok(entityModel);
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados da participação"),
            @ApiResponse(responseCode = "201", description = "Participação cadastrada com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Forbidden! Requer autenticação")
    })
    @Operation(summary = "Cadastra uma nova Participação no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados" +
            " necessários para realizar o cadastro de uma nova participação, o endpoint funciona quando um voluntário quer entrar em uma partida," +
            " por esse motivo que o id informado precisa ser o mesmo id do voluntário que está autenticado, pois não posso entrar em uma partida por outra pessoa")
    public ResponseEntity<EntityModel<ParticipacaoResponseDTO>> create(@ParameterObject @RequestBody @Valid ParticipacaoCreateDTO participacaoCreateDTO){
        Participacao participacaoSaved = service.create(participacaoCreateDTO);
        ParticipacaoResponseDTO participacaoResponseDTO = new ParticipacaoResponseDTO(participacaoSaved);
        EntityModel<ParticipacaoResponseDTO> entityModel = EntityModel.of(participacaoResponseDTO);
        entityModel.add(linkTo(methodOn(ParticipacaoController.class).get(participacaoSaved.getId())).withSelfRel());
        entityModel.add(linkTo(methodOn(ParticipacaoController.class).index(Pageable.unpaged())).withRel("contents"));
        URI uriLocation = linkTo(methodOn(ParticipacaoController.class).get(participacaoSaved.getId())).toUri();
        return ResponseEntity.created(uriLocation).body(entityModel);
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Participação não encontrada"),
            @ApiResponse(responseCode = "204", description = "Participação removida com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Forbidden! Requer autenticação e o id informado precisa ser o mesmo id do voluntario authenticado")
    })
    @Operation(summary = "Deleta uma participação do sistema", description = "Endpoint recebe no path o id da participação a ser deletada")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
