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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/participacoes")
@Tag(name = "participações", description = "Controller relacionado aos dados de participações")
public class ParticipacaoController {

    @Autowired
    private ParticipacaoService service;

    @GetMapping
    @Operation(summary = "Lista todas as participações", description = "A listagem ocorre com paginação e tamanho padrão de 10 participações por requisição," +
            "requer autenticação")
    public ResponseEntity<Page<ParticipacaoResponseDTO>> index(@ParameterObject @PageableDefault(size = 10) Pageable pageable){
        Page<ParticipacaoResponseDTO> participacoes = service.index(pageable).map(ParticipacaoResponseDTO::new);
        return ResponseEntity.ok(participacoes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "exibe os detalhes da participação de id equivalente ao informado",description = "Endpoint retorna um objeto contendo os detalhes de uma participação e requer autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Participação não encontrada"),
            @ApiResponse(responseCode = "200", description = "Participação detalhada com sucesso!")
    })
    public ResponseEntity<ParticipacaoResponseDTO> get(@PathVariable Long id){
        Participacao participacao = service.get(id);
        return ResponseEntity.ok(new ParticipacaoResponseDTO(participacao));
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
    public ResponseEntity<Void> create(@ParameterObject @RequestBody @Valid ParticipacaoCreateDTO participacaoCreateDTO){
        Participacao participacaoSaved = service.create(participacaoCreateDTO);
        URI uriLocation = Uri.createUriLocation(participacaoSaved.getId());
        return ResponseEntity.created(uriLocation).build();
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
