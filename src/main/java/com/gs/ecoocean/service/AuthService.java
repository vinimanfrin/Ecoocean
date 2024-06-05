package com.gs.ecoocean.service;

import com.gs.ecoocean.model.User;
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

    public Auth findByToken() {
        User authenticatedUser = UserService.authenticated();
        Auth auth = repository.findByUsername(authenticatedUser.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("Não authorizado"));

        return auth;
    }
}
