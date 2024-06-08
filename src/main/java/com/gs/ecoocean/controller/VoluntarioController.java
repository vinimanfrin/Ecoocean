package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.voluntario.VoluntarioCreateDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioRankingDTO;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/voluntarios")
@Tag(name = "voluntários", description = "Controller relacionado aos dados de voluntários")
public class VoluntarioController {

    @Autowired
    private VoluntarioService service;

    @GetMapping
    @Operation(summary = "Lista todos os voluntários", description = "A listagem ocorre com paginação e tamanho padrão de 10 voluntários por requisição," +
            "endpoint requer autenticação")
    public ResponseEntity<Page<VoluntarioResponseDTO>> index(@ParameterObject @PageableDefault(size = 10) Pageable pageable){
        Page<VoluntarioResponseDTO> voluntarios = service.index(pageable).map(VoluntarioResponseDTO::new);
        return ResponseEntity.ok(voluntarios);
    }

    @GetMapping("/{id}")
    @Operation(summary = "exibe os detalhes do voluntário de id equivalente ao informado",description = "Endpoint retorna um objeto contendo os detalhes de um voluntário e requer autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Voluntário não encontrado"),
            @ApiResponse(responseCode = "200", description = "Voluntário detalhado com sucesso!"),
            @ApiResponse(responseCode = "401|403", description = "Não autorizado! Requer autenticação")
    })
    public ResponseEntity<VoluntarioResponseDTO> get(@PathVariable Long id){
        Voluntario voluntario = service.get(id);
        return ResponseEntity.ok(new VoluntarioResponseDTO(voluntario));
    }

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do voluntário"),
            @ApiResponse(responseCode = "201", description = "Voluntário cadastrado com sucesso!"),
            @ApiResponse(responseCode = "401|403", description = "Não authorizado! Requer autenticação")
    })
    @Operation(summary = "Cadastra um novo Voluntário no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados" +
            " necessários para realizar o cadastro de um novo voluntário")
    public ResponseEntity<Void> create(@ParameterObject @RequestBody @Valid VoluntarioCreateDTO voluntarioDTO){
        Voluntario voluntarioSaved = service.create(voluntarioDTO);
        URI uriLocation = Uri.createUriLocation(voluntarioSaved.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados do voluntário"),
            @ApiResponse(responseCode = "201", description = "Voluntário atualizado com sucesso!"),
            @ApiResponse(responseCode = "401|403", description = "Não authorizado! Requer autenticação")
    })
    @Operation(summary = "Atualiza um Voluntário no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados" +
            " necessários para atualizar um voluntário")
    public ResponseEntity<VoluntarioResponseDTO> update(@PathVariable Long id, @ParameterObject @RequestBody @Valid VoluntarioUpdateDTO voluntarioUpdateDTO){
        Voluntario voluntarioUpdated = service.update(voluntarioUpdateDTO,id);
        return ResponseEntity.ok(new VoluntarioResponseDTO(voluntarioUpdated));
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

    @GetMapping("/ranking")
    public List<VoluntarioRankingDTO> getVoluntarioRanking() {
        return service.getVoluntarioRanking();
    }
}
