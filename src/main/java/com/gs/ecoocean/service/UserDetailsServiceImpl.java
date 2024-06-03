package com.gs.ecoocean.service;

import com.gs.ecoocean.model.Auth;
import com.gs.ecoocean.model.User;
import com.gs.ecoocean.repository.AuthRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Auth> optionalAuth = repository.findByUsername(username);
        if (!optionalAuth.isPresent()) throw new UsernameNotFoundException(username);

        Auth auth = optionalAuth.get();
        return new User(auth.getId(),auth.getUsername(),auth.getPassword(),auth.getRole());
    }
}
