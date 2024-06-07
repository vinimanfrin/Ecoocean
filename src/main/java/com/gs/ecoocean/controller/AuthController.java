package com.gs.ecoocean.controller;

import com.gs.ecoocean.dto.VoluntarioAutenticadoDTO;
import com.gs.ecoocean.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService service;

    @GetMapping
    public ResponseEntity<VoluntarioAutenticadoDTO> findByToken(){
        VoluntarioAutenticadoDTO voluntarioAutenticado = service.findByToken();
        return ResponseEntity.ok(voluntarioAutenticado);
    }
}
