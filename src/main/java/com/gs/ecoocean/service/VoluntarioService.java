package com.gs.ecoocean.service;

import com.gs.ecoocean.dto.voluntario.VoluntarioCreateDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioUpdateDTO;
import com.gs.ecoocean.model.Voluntario;
import com.gs.ecoocean.repository.VoluntarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VoluntarioService {

    @Autowired
    private VoluntarioRepository repository;

    public Page<Voluntario> index(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Voluntario get(Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Voluntário não encontrado para o id:"+id));
    }

    public Voluntario create(VoluntarioCreateDTO voluntarioDTO){
        return repository.save(new Voluntario(voluntarioDTO));
    }

    public void deleteById(Long id){
        Voluntario voluntario = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "não foi possível deletar o voluntário, voluntário não encontrado para o id:"+id));

        repository.deleteById(id);
    }

    public Voluntario update(VoluntarioUpdateDTO voluntarioUpdateDTO, Long id){
        Voluntario voluntario = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "não foi possível atualizar os dados do voluntário, voluntário não encontrado para o id:"+id));

        voluntario.update(voluntarioUpdateDTO);
        return repository.save(voluntario);
    }
}
