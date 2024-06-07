package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.partida.PartidaCreateDTO;
import com.gs.ecoocean.dto.partida.PartidaResponseDTO;
import com.gs.ecoocean.dto.partida.PartidaUpdateDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioCreateDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioResponseDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioUpdateDTO;
import com.gs.ecoocean.model.Partida;
import com.gs.ecoocean.model.Voluntario;
import com.gs.ecoocean.service.PartidaService;
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
@RequestMapping("/partidas")
@Tag(name = "partidas", description = "Controller responsável pelos dados de partidas")
public class PartidaController {

    @Autowired
    private PartidaService service;

    @GetMapping
    @Operation(summary = "Lista todas as partidas", description = "A listagem ocorre com paginação e tamanho padrão de 10 partidas por requisição")
    public ResponseEntity<Page<PartidaResponseDTO>> index(@ParameterObject @PageableDefault(size = 10) Pageable pageable){
        Page<PartidaResponseDTO> partidas = service.index(pageable).map(PartidaResponseDTO::new);
        return ResponseEntity.ok(partidas);
    }

    @GetMapping("/{id}")
    @Operation(summary = "exibe os detalhes da partida de id equivalente ao informado",description = "Endpoint retorna um objeto contendo os detalhes de uma partida e não requer autenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Partida não encontrada"),
            @ApiResponse(responseCode = "200", description = "Partida detalhada com sucesso!")
    })
    public ResponseEntity<PartidaResponseDTO> get(@PathVariable Long id){
        Partida partida = service.get(id);
        return ResponseEntity.ok(new PartidaResponseDTO(partida));
    }

    @PostMapping
    @PreAuthorize(value = "hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados da partida"),
            @ApiResponse(responseCode = "201", description = "Partida cadastrada com sucesso! (status agendada)"),
            @ApiResponse(responseCode = "403|401", description = "Forbidden! Requer autenticação e role de admin"),
            @ApiResponse(responseCode = "404", description = "Área ou voluntário admin não encontrado")
    })
    @Operation(summary = "Cadastra uma nova partida no sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados" +
            " necessários para realizar o cadastro de uma nova partida")
    public ResponseEntity<Void> create(@ParameterObject @RequestBody @Valid PartidaCreateDTO partidaDTO){
        Partida partidaSaved = service.create(partidaDTO);
        URI uriLocation = Uri.createUriLocation(partidaSaved.getId());
        return ResponseEntity.created(uriLocation).build();
    }

    @PutMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "400", description = "Erro de validação nos dados da partida"),
            @ApiResponse(responseCode = "200", description = "Partida atualizada com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Forbidden! Requer autenticação e role de admin")
    })
    @Operation(summary = "Atualiza uma partida do sistema", description = "Endpoint recebe no corpo da requisição um objeto contendo os dados necessários para atualizar dados de uma partida")
    public ResponseEntity<PartidaResponseDTO> update(@PathVariable Long id, @ParameterObject @RequestBody @Valid PartidaUpdateDTO partidaUpdateDTO){
        Partida partidaUpdated = service.update(partidaUpdateDTO,id);
        return ResponseEntity.ok(new PartidaResponseDTO(partidaUpdated));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Partida não encontrada"),
            @ApiResponse(responseCode = "204", description = "Partida removida(cancelada) com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Forbidden! Requer autenticação e role de admin")
    })
    @Operation(summary = "Deleta uma partida do sistema", description = "Endpoint recebe no path o id da partida a ser deletada")
    public ResponseEntity<Void> cancelar(@PathVariable Long id){
        service.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/encerrar-ativar/{id}")
    @PreAuthorize(value = "hasRole('ADMIN')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "404", description = "Partida não encontrada"),
            @ApiResponse(responseCode = "400", description = "Erro de validação dos dados da partida"),
            @ApiResponse(responseCode = "204", description = "Partida iniciada ou encerrada com sucesso!"),
            @ApiResponse(responseCode = "403|401", description = "Forbidden! Requer autenticação e role de admin")
    })
    @Operation(summary = "Inicia ou Encerra uma partida do sistema", description = "Endpoint recebe no path o id da partida a ser iniciada ou encerrada" +
            "é possível iniciar apenas partidas que estejam agendadas, e encerrar apenas partidas que estejam em andamento")
    public ResponseEntity<Void> encerrarOuIniciar(@PathVariable Long id){
        service.encerrarOuAtivar(id);
        return ResponseEntity.noContent().build();
    }
}
