package com.gs.ecoocean.service;

import com.gs.ecoocean.dto.voladmin.VoluntarioAdminCreateDTO;
import com.gs.ecoocean.dto.voladmin.VoluntarioAdminUpdateDTO;
import com.gs.ecoocean.model.VoluntarioAdmin;
import com.gs.ecoocean.repository.VoluntarioAdminRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class VoluntarioAdminService {

    @Autowired
    private VoluntarioAdminRepository repository;

    public Page<VoluntarioAdmin> index(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public VoluntarioAdmin get(Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Voluntário administrador não encontrado para o id:"+id));
    }

    public VoluntarioAdmin create(VoluntarioAdminCreateDTO voluntarioAdminDTO){
        return repository.save(new VoluntarioAdmin(voluntarioAdminDTO));
    }

    public void deleteById(Long id){
        VoluntarioAdmin voluntario = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "não foi possível deletar o voluntário administrador, voluntário administrador não encontrado para o id:"+id));

        repository.deleteById(id);
    }

    public VoluntarioAdmin update(VoluntarioAdminUpdateDTO voluntarioAdminUpdateDTO, Long id){
        VoluntarioAdmin voluntarioAdmin = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "não foi possível atualizar os dados do voluntário administrador, voluntário administrador não encontrado para o id:"+id));

        voluntarioAdmin.update(voluntarioAdminUpdateDTO);
        return repository.save(voluntarioAdmin);
    }
}
