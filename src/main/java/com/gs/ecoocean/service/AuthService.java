package com.gs.ecoocean.service;

import com.gs.ecoocean.dto.VoluntarioAutenticadoDTO;
import com.gs.ecoocean.exceptions.AuthorizationException;
import com.gs.ecoocean.model.User;
import com.gs.ecoocean.model.Voluntario;
import com.gs.ecoocean.model.VoluntarioAdmin;
import com.gs.ecoocean.model.enuns.PerfilUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs.ecoocean.model.Auth;
import com.gs.ecoocean.repository.AuthRepository;

@Service
public class AuthService {

    @Autowired
    private AuthRepository repository;

    @Autowired
    private VoluntarioService voluntarioService;

    @Autowired
    private VoluntarioAdminService voluntarioAdminService;

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public Auth create(Auth auth) {
        return repository.save(auth);
    }

    public VoluntarioAutenticadoDTO findByToken() {
        User authenticatedUser = UserService.authenticated();
        Auth auth = repository.findByUsername(authenticatedUser.getUsername()).orElseThrow(
                () -> new AuthorizationException("Não authorizado"));
        VoluntarioAutenticadoDTO voluntarioAutenticadoDTO = null;
        if (auth.getRole().equals(PerfilUsuario.VOLUNTARIO)) {
            Voluntario voluntario = voluntarioService.findByAuthId(auth.getId());
            voluntarioAutenticadoDTO = new VoluntarioAutenticadoDTO(voluntario.getId(),voluntario.getNome(),voluntario.getEmail(),auth);
        }
        else {
            VoluntarioAdmin voluntarioAdmin = voluntarioAdminService.findByAuthId(auth.getId());
            voluntarioAutenticadoDTO = new VoluntarioAutenticadoDTO(voluntarioAdmin.getId(), voluntarioAdmin.getNome(), voluntarioAdmin.getEmail(),auth);
        }

        return voluntarioAutenticadoDTO;
    }
}
