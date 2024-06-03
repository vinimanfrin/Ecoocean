package com.gs.ecoocean.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gs.ecoocean.model.Auth;
import com.gs.ecoocean.repository.AuthRepository;

@Service
public class AuthService {

    @Autowired
    private AuthRepository repository;

    public boolean existsByUsername(String username) {
        return repository.existsByUsername(username);
    }

    public Auth create(Auth auth) {
        return repository.save(auth);
    }
}
