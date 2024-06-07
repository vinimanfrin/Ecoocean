package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.VoluntarioAutenticadoDTO;
import com.gs.ecoocean.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "auths", description = "Controller relacionado aos dados de autenticação dos voluntários")
public class AuthController {

    @Autowired
    private AuthService service;

    @GetMapping
    @Operation(summary = "exibe os detalhes do voluntário autenticado",description = "Endpoint retorna um objeto contendo os detalhes de um voluntario(admin ou não) e requer authenticação")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "403", description = "Token inválido"),
            @ApiResponse(responseCode = "200", description = "Voluntário detalhado com sucesso!")
    })
    public ResponseEntity<VoluntarioAutenticadoDTO> findByToken(){
        VoluntarioAutenticadoDTO voluntarioAutenticado = service.findByToken();
        return ResponseEntity.ok(voluntarioAutenticado);
    }
}
