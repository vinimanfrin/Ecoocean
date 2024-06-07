package com.gs.ecoocean.repository;

import com.gs.ecoocean.model.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Long> {

    Optional<Voluntario> findByAuthId(Long authId);
}
