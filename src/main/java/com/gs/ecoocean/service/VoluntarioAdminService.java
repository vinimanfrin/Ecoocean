package com.gs.ecoocean.service;

import com.gs.ecoocean.dto.voladmin.VoluntarioAdminCreateDTO;
import com.gs.ecoocean.dto.voladmin.VoluntarioAdminUpdateDTO;
import com.gs.ecoocean.exceptions.DataIntegrityException;
import com.gs.ecoocean.exceptions.ObjectNotFoundException;
import com.gs.ecoocean.model.Auth;
import com.gs.ecoocean.model.VoluntarioAdmin;
import com.gs.ecoocean.model.enuns.PerfilUsuario;
import com.gs.ecoocean.repository.VoluntarioAdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VoluntarioAdminService {

    @Autowired
    private VoluntarioAdminRepository repository;

    @Autowired
    @Lazy
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<VoluntarioAdmin> index(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public VoluntarioAdmin get(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Voluntário administrador não encontrado para o id:"+id));
    }

    @Transactional
    public VoluntarioAdmin create(VoluntarioAdminCreateDTO voluntarioAdminDTO){
        if (authService.existsByUsername(voluntarioAdminDTO.username())) {
            throw new DataIntegrityException("o username informado está em uso");
        }
        String passwordEncoded = passwordEncoder.encode(voluntarioAdminDTO.password());
        Auth auth = new Auth(voluntarioAdminDTO.username(),passwordEncoded,PerfilUsuario.ADMIN);
        authService.create(auth);

        return repository.save(new VoluntarioAdmin(voluntarioAdminDTO, auth));
    }

    public void deleteById(Long id){
        VoluntarioAdmin voluntario = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "não foi possível deletar o voluntário administrador, voluntário administrador não encontrado para o id:"+id));

        repository.deleteById(id);
    }

    public VoluntarioAdmin update(VoluntarioAdminUpdateDTO voluntarioAdminUpdateDTO, Long id){
        VoluntarioAdmin voluntarioAdmin = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "não foi possível atualizar os dados do voluntário administrador, voluntário administrador não encontrado para o id:"+id));

        voluntarioAdmin.update(voluntarioAdminUpdateDTO);
        return repository.save(voluntarioAdmin);
    }

    public VoluntarioAdmin findByAuthId(Long authId){
        return repository.findByAuthId(authId).orElseThrow(() -> new ObjectNotFoundException("Voluntário não encontrado com o auth de id:"+authId));
    }
}
