package com.gs.ecoocean.repository;

import com.gs.ecoocean.model.VoluntarioAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoluntarioAdminRepository extends JpaRepository<VoluntarioAdmin, Long> {
    Optional<VoluntarioAdmin> findByAuthId(Long authId);
}
