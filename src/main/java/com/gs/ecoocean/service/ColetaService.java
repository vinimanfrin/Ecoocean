package com.gs.ecoocean.service;

import com.gs.ecoocean.dto.coleta.ColetaCreateDTO;
import com.gs.ecoocean.exceptions.DataIntegrityException;
import com.gs.ecoocean.exceptions.ObjectNotFoundException;
import com.gs.ecoocean.model.Coleta;
import com.gs.ecoocean.model.Participacao;
import com.gs.ecoocean.model.enuns.StatusPartida;
import com.gs.ecoocean.model.enuns.TipoLixo;
import com.gs.ecoocean.repository.ColetaRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ColetaService {

    @Autowired
    private ColetaRepository repository;

    @Autowired
    private ParticipacaoService participacaoService;

    public Page<Coleta> index(Pageable pageable) {
        return repository.findAll(pageable);
    }

    public Coleta get(Long id){
        return repository.findById(id).orElseThrow(() -> new ObjectNotFoundException("coleta não encontrada para o id:"+id));
    }

    @Transactional
    public Coleta create(ColetaCreateDTO coletaCreateDTO){
        Participacao participacao = participacaoService.get(coletaCreateDTO.idParticipacao());

        if (!participacao.getPartida().getStatus().equals(StatusPartida.ATIVA))
            throw new DataIntegrityException("a inserção de uma nova coleta é permitida apenas em partidas em andamento");
        
        BigDecimal valorTipoLixo = new BigDecimal(TipoLixo.toEnum(coletaCreateDTO.tipoLixo()).getValor());
        BigDecimal pontuacao = valorTipoLixo.multiply(coletaCreateDTO.quantidade());

        participacao.setPontuacao(participacao.getPontuacao().add(pontuacao));

        return repository.save(new Coleta(coletaCreateDTO,participacao,pontuacao));
    }

    public void deleteById(Long id){
         Coleta coleta = repository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "não foi possível deletar a coleta, coleta não encontrada para o id:"+id));
         if (!coleta.getParticipacao().getPartida().getStatus().equals(StatusPartida.ATIVA))
             throw new DataIntegrityException("É permitido deletar coletas apenas de partidas em andamento");

        repository.deleteById(id);
    }
}
