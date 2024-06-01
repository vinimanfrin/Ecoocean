package com.gs.ecoocean.model;

import com.gs.ecoocean.dto.partida.PartidaCreateDTO;
import com.gs.ecoocean.dto.partida.PartidaUpdateDTO;
import com.gs.ecoocean.model.enuns.StatusPartida;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "partidas")
@NoArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne
    @JoinColumn(name = "voluntario_admin_id")
    private VoluntarioAdmin voluntarioAdmin;

    @OneToMany(mappedBy = "partida", cascade = CascadeType.ALL)
    private List<Participacao> participacoes;

    public Partida(Long id, String nome, String descricao, LocalDateTime dataInicio, LocalDateTime dataFim, StatusPartida status, Area area, VoluntarioAdmin voluntarioAdmin){
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status == null ? null : status.getCodigo();
        this.area = area;
        this.voluntarioAdmin = voluntarioAdmin;
    }

    public Partida(PartidaCreateDTO partidaDTO, Area area, VoluntarioAdmin voluntarioAdmin) {
        this.nome = partidaDTO.nome();
        this.descricao = partidaDTO.descricao();
        this.status = StatusPartida.AGENDADA.getCodigo();
        this.area = area;
        this.voluntarioAdmin = voluntarioAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDateTime dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDateTime getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDateTime dataFim) {
        this.dataFim = dataFim;
    }

    public StatusPartida getStatus() {
        return StatusPartida.toEnum(status);
    }

    public void setStatus(StatusPartida status) {
        this.status = status.getCodigo();
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public VoluntarioAdmin getVoluntarioAdmin() {
        return voluntarioAdmin;
    }

    public void setVoluntarioAdmin(VoluntarioAdmin voluntarioAdmin) {
        this.voluntarioAdmin = voluntarioAdmin;
    }

    public List<Participacao> getParticipacoes() {
        return participacoes;
    }

    public void setParticipacoes(List<Participacao> participacoes) {
        this.participacoes = participacoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void update(PartidaUpdateDTO partidaUpdateDTO) {
        this.nome = nome;
        this.descricao = descricao;
    }
}
