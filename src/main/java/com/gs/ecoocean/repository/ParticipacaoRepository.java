package com.gs.ecoocean.repository;

import com.gs.ecoocean.model.Participacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipacaoRepository extends JpaRepository<Participacao,Long> {

    @Query("SELECT COUNT(p) > 0 FROM Participacao p WHERE p.partida.id = :partidaId AND p.voluntario.id = :voluntarioId")
    boolean existsByPartidaAndVoluntario(@Param("partidaId") Long partidaId, @Param("voluntarioId") Long voluntarioId);
}
