package com.gs.ecoocean.model;

import com.gs.ecoocean.model.enuns.PerfilUsuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
public class Auth {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    
    private String password;
    private Integer role;

	public Auth(String username,String password,PerfilUsuario role){
		this.username = username;
		this.password = password;
		this.role = role.getCodigo();
	}

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public PerfilUsuario getRole() {
		return PerfilUsuario.toEnum(this.role);
	}

	public void setRole(PerfilUsuario role) {
		this.role = role.getCodigo();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
