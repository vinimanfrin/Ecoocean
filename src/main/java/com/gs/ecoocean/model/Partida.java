package com.gs.ecoocean.model;

import com.gs.ecoocean.model.enuns.StatusPartida;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "partidas")
@NoArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime dataInicio;
    private LocalDateTime dataFim;
    private Integer status;

    @ManyToOne
    @JoinColumn(name = "area_id")
    private Area area;

    @ManyToOne
    @JoinColumn(name = "voluntario_admin_id")
    private VoluntarioAdmin voluntarioAdmin;

    @OneToMany(mappedBy = "partida")
    private List<Participacao> participacoes;

    public Partida(Long id, LocalDateTime dataInicio, LocalDateTime dataFim, StatusPartida status, Area area, VoluntarioAdmin voluntarioAdmin){
        this.id = id;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.status = status == null ? null : status.getCodigo();
        this.area = area;
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
}
