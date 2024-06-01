package com.gs.ecoocean.repository;

import com.gs.ecoocean.model.VoluntarioAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VoluntarioAdminRepository extends JpaRepository<VoluntarioAdmin, Long> {
}
