package com.gs.ecoocean.service;

import com.gs.ecoocean.dto.area.AreaCreateDTO;
import com.gs.ecoocean.dto.area.AreaUpdateDTO;
import com.gs.ecoocean.exceptions.ObjectNotFoundException;
import com.gs.ecoocean.model.Area;
import com.gs.ecoocean.repository.AreaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class AreaService {

    @Autowired
    private AreaRepository repository;

    public Page<Area> index(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Area get(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("área não encontrada para o id:"+id));
    }

    public Area create(AreaCreateDTO areaDTO){
        return repository.save(new Area(areaDTO));
    }

    public void deleteById(Long id){
        Area area = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "não foi possível deletar a área, área não encontrada para o id:"+id));

        repository.deleteById(id);
    }

    public Area update(AreaUpdateDTO areaUpdateDTO, Long id){
        Area area = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "não foi possível atualizar os dados da área, área não encontrada para o id:"+id));

        area.update(areaUpdateDTO);
        return repository.save(area);
    }
}
