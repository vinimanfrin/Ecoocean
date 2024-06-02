package com.gs.ecoocean.service;

import com.gs.ecoocean.dto.participacao.ParticipacaoCreateDTO;
import com.gs.ecoocean.model.Participacao;
import com.gs.ecoocean.model.Partida;
import com.gs.ecoocean.model.Voluntario;
import com.gs.ecoocean.model.enuns.StatusPartida;
import com.gs.ecoocean.repository.ParticipacaoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ParticipacaoService {

    @Autowired
    private ParticipacaoRepository repository;

    @Autowired
    private VoluntarioService voluntarioService;

    @Autowired
    private PartidaService partidaService;

    public Page<Participacao> index(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Participacao get(Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("participacao não encontrada para o id:"+id));
    }

    public Participacao create(ParticipacaoCreateDTO participacaoCreateDTO){
        Partida partida = partidaService.get(participacaoCreateDTO.idPartida());
        if (!partida.getStatus().equals(StatusPartida.AGENDADA) && !partida.getStatus().equals(StatusPartida.ATIVA))
            throw new DataIntegrityViolationException("Não é possível inserir participações em partidas encerradas ou canceladas");

        Voluntario voluntario = voluntarioService.get(participacaoCreateDTO.idVoluntario());
        if (existsByPartidaAndVoluntario(partida.getId(),voluntario.getId()))
            throw new DataIntegrityViolationException("Esse voluntário já está na partida");

        return repository.save(new Participacao(voluntario,partida));
    }

    public void deleteById(Long id){
        Participacao participacao = repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                "não foi possível deletar a participação, participação não encontrada para o id:"+id));

        repository.deleteById(id);
    }

    public boolean existsByPartidaAndVoluntario(Long partidaId,Long voluntarioId){
        return repository.existsByPartidaAndVoluntario(partidaId,voluntarioId);
    }
}
