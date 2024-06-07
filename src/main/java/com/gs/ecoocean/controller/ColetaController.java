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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/coletas")
@Tag(name = "Controller relacionado aos dados de coletas")
public class ColetaController {

    @Autowired
    private ColetaService service;

    @GetMapping
    @Operation(summary = "Lista todas as coletas", description = "A listagem ocorre com paginação e tamanho padrão de 10 coletas por requisição," +
            "requer authenticação")
    public ResponseEntity<Page<ColetaResponseDTO>> index(@ParameterObject @PageableDefault(size = 10) Pageable pageable){
        Page<ColetaResponseDTO> coletas = service.index(pageable).map(ColetaResponseDTO::new);
        return ResponseEntity.ok(coletas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "exibe os detalhes da coleta de id equivalente ao informado",description = "Endpoint retorna um objeto contendo os detalhes de uma coleta e requer autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Coleta não encontrada"),
            @ApiResponse(responseCode = "200", description = "Coleta detalhada com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Não autorizado, requer authenticação")
    })
    public ResponseEntity<ColetaResponseDTO> get(@PathVariable Long id){
        Coleta coleta = service.get(id);
        return ResponseEntity.ok(new ColetaResponseDTO(coleta));
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
    public ResponseEntity<Void> create(@ParameterObject @RequestBody @Valid ColetaCreateDTO coletaCreateDTO){
        Coleta coletaSaved = service.create(coletaCreateDTO);
        URI uriLocation = Uri.createUriLocation(coletaSaved.getId());
        return ResponseEntity.created(uriLocation).build();
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
