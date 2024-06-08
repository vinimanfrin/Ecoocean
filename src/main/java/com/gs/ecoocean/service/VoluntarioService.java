package com.gs.ecoocean.service;

import com.gs.ecoocean.dto.voluntario.VoluntarioCreateDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioRankingDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioUpdateDTO;
import com.gs.ecoocean.exceptions.DataIntegrityException;
import com.gs.ecoocean.exceptions.ObjectNotFoundException;
import com.gs.ecoocean.model.Auth;
import com.gs.ecoocean.model.User;
import com.gs.ecoocean.model.Voluntario;
import com.gs.ecoocean.model.enuns.PerfilUsuario;
import com.gs.ecoocean.repository.VoluntarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VoluntarioService {

    @Autowired
    private VoluntarioRepository repository;

    @Autowired
    @Lazy
    private AuthService authService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Page<Voluntario> index(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Voluntario get(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Voluntário não encontrado para o id:"+id));
    }

    public Voluntario create(VoluntarioCreateDTO voluntarioDTO){
        if (authService.existsByUsername(voluntarioDTO.username())){
            throw new DataIntegrityException("o username informado está em uso");
        }
        String passwordEncoded = passwordEncoder.encode(voluntarioDTO.password());
        Auth auth = new Auth(voluntarioDTO.username(),passwordEncoded,PerfilUsuario.VOLUNTARIO);
        authService.create(auth);

        return repository.save(new Voluntario(voluntarioDTO, auth));
    }

    public void deleteById(Long id){
        Voluntario voluntario = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "não foi possível deletar o voluntário, voluntário não encontrado para o id:"+id));

        repository.deleteById(id);
    }

    public Voluntario update(VoluntarioUpdateDTO voluntarioUpdateDTO, Long id){
        Voluntario voluntario = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "não foi possível atualizar os dados do voluntário, voluntário não encontrado para o id:"+id));

        User authenticatedUser = UserService.authenticated();
        if (!authenticatedUser.hasRole(PerfilUsuario.ADMIN) && !voluntario.getAuth().getId().equals(authenticatedUser.getId()))
            throw new DataIntegrityException("Voluntários alteram apenas os próprios dados");

        voluntario.update(voluntarioUpdateDTO);
        return repository.save(voluntario);
    }

    public Voluntario findByAuthId(Long authId){
        return repository.findByAuthId(authId).orElseThrow(() -> new ObjectNotFoundException("Voluntário não encontrado com o auth de id:"+authId));
    }

    public List<VoluntarioRankingDTO> getVoluntarioRanking() {
        return repository.findTop5VoluntarioRanking();
    }
}
