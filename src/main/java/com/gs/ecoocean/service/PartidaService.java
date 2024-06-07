package com.gs.ecoocean.service;

import com.gs.ecoocean.dto.partida.PartidaCreateDTO;
import com.gs.ecoocean.dto.partida.PartidaUpdateDTO;
import com.gs.ecoocean.exceptions.DataIntegrityException;
import com.gs.ecoocean.exceptions.ObjectNotFoundException;
import com.gs.ecoocean.model.Area;
import com.gs.ecoocean.model.Partida;
import com.gs.ecoocean.model.VoluntarioAdmin;
import com.gs.ecoocean.model.enuns.StatusPartida;
import com.gs.ecoocean.repository.PartidaRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PartidaService {

    @Autowired
    private PartidaRepository repository;

    @Autowired
    private AreaService areaService;

    @Autowired
    private VoluntarioAdminService voluntarioAdminService;

    @Transactional(readOnly = true)
    public Page<Partida> index(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Partida get(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Partida não encontrada para o id:"+id));
    }

    @Transactional
    public Partida create(PartidaCreateDTO partidaDTO){
        Area area = areaService.get(partidaDTO.areaId());
        VoluntarioAdmin voluntarioAdmin = voluntarioAdminService.get(partidaDTO.voluntarioAdminId());
        return repository.save(new Partida(partidaDTO,area,voluntarioAdmin));
    }

    @Transactional
    public void cancelar(Long id){
        Partida partida = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "não foi possível cancelar a partida, partida não encontrada para o id:"+id));

        if (!partida.getStatus().equals(StatusPartida.AGENDADA)) throw new ObjectNotFoundException(
                "O cancelamento de partidas é possível apenas em partidas que estão agendadas");

        partida.setStatus(StatusPartida.CANCELADA);
        repository.save(partida);
    }

    @Transactional
    public Partida update(PartidaUpdateDTO partidaUpdateDTO, Long id){
        Partida partida = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "não foi possível atualizar os dados da partida, partida não encontrada para o id:"+id));

        if (partida.getStatus().equals(StatusPartida.CANCELADA) || partida.getStatus().equals(StatusPartida.ENCERRADA))
            throw new DataIntegrityException("Não é possível alterar dados de partidas canceladas ou encerradas");

        partida.update(partidaUpdateDTO);
        return repository.save(partida);
    }

    @Transactional
    public void encerrarOuAtivar(Long id){
        Partida partida = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "não foi possível encerrar/ativar a partida, partida não encontrada para o id:"+id));

        if (partida.getStatus().equals(StatusPartida.ATIVA))
            partida.setStatus(StatusPartida.ENCERRADA);
        else if (partida.getStatus().equals(StatusPartida.AGENDADA))
            partida.setStatus(StatusPartida.ATIVA);
        else
            throw new DataIntegrityException("não é possível ativar/encerrar partidas encerradas ou canceladas");
        
        repository.save(partida);
    }
}
