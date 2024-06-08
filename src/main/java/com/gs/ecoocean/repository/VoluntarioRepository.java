package com.gs.ecoocean.repository;

import com.gs.ecoocean.dto.voluntario.VoluntarioRankingDTO;
import com.gs.ecoocean.model.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {

    Optional<Voluntario> findByAuthId(Long authId);

    @Query("SELECT new com.gs.ecoocean.dto.voluntario.VoluntarioRankingDTO(v.id, v.nome, SUM(p.pontuacao)) " +
            "FROM Voluntario v JOIN v.participacoes p " +
            "GROUP BY v.id, v.nome " +
            "ORDER BY SUM(p.pontuacao) DESC")
    List<VoluntarioRankingDTO> findTop5VoluntarioRanking();
}
