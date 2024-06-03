package com.gs.ecoocean.model;

import java.util.Arrays;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.gs.ecoocean.model.enuns.PerfilUsuario;

public class User implements UserDetails{

    private Long id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;

	public User() {
		super();
	}

	public User(Long id, String username, String password, PerfilUsuario perfil) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.authorities = Arrays.asList(new SimpleGrantedAuthority(perfil.getDescricao()));
	}

	public Long getId() {
		return id;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public boolean hasRole(PerfilUsuario perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.getDescricao()));
	}
}
