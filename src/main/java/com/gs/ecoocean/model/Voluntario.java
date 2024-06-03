package com.gs.ecoocean.model;

import com.gs.ecoocean.dto.voluntario.VoluntarioCreateDTO;
import com.gs.ecoocean.dto.voluntario.VoluntarioUpdateDTO;
import com.gs.ecoocean.model.enuns.Sexo;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "voluntarios")
@NoArgsConstructor
public class Voluntario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataNascimento;
    private String email;
    private Integer sexo;

    @OneToMany(mappedBy = "voluntario")
    private List<Participacao> participacoes;

    @OneToOne
    @JoinColumn(name = "auth_id")
    private Auth auth;

    public Voluntario(Long id, String nome, LocalDate dataNascimento, String email, Sexo sexo, Auth auth){
        this.id = id;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.sexo = sexo == null ? null : sexo.getCodigo();
        this.auth = auth;
    }

    public Voluntario(VoluntarioCreateDTO voluntarioDTO, Auth auth) {
        this.nome = voluntarioDTO.nome();
        this.dataNascimento = voluntarioDTO.dataNascimento();
        this.email = voluntarioDTO.email();
        this.sexo = Sexo.toEnum(voluntarioDTO.sexo()).getCodigo();
        this.auth = auth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Sexo getSexo() {
        return Sexo.toEnum(sexo);
    }

    public void setSexo(Sexo sexo) {
        this.sexo = sexo.getCodigo();
    }

    public List<Participacao> getParticipacoes() {
        return participacoes;
    }

    public void setParticipacoes(List<Participacao> participacoes) {
        this.participacoes = participacoes;
    }

    public Auth getAuth() {
        return auth; 
    }

    public void setAuth(Auth auth) { 
        this.auth = auth;
    }

    public void update(VoluntarioUpdateDTO voluntarioUpdateDTO) {
        this.email = voluntarioUpdateDTO.email();
    }
}
