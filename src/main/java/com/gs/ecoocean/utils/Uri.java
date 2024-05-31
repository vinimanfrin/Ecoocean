package com.gs.ecoocean.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class Uri {

    public static URI createUriLocation(Long id){
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/"+id)
                .build()
                .toUri();
    }
}
