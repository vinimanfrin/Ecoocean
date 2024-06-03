package com.gs.ecoocean.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gs.ecoocean.model.Auth;

@Repository
public interface AuthRepository extends JpaRepository<Auth,Long>{
}
