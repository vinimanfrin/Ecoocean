package com.gs.ecoocean.service;

import com.gs.ecoocean.model.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    public static User authenticated(){
        try{
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
        catch(Exception exception){
            return null;
        }

    }
}
