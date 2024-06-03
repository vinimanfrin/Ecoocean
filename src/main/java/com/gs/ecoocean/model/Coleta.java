package com.gs.ecoocean.model;

import java.math.BigDecimal;
import com.gs.ecoocean.dto.coleta.ColetaCreateDTO;
import com.gs.ecoocean.model.enuns.TipoLixo;
import jakarta.persistence.*;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "coletas")
@NoArgsConstructor
public class Coleta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer tipoLixo;
    private BigDecimal quantidade;
    private BigDecimal pontuacao;

    @ManyToOne
    @JoinColumn(name = "participacao_id")
    private Participacao participacao;

    public Coleta(ColetaCreateDTO coletaCreateDTO, Participacao participacao, BigDecimal pontuacao) {
        this.tipoLixo = TipoLixo.toEnum(coletaCreateDTO.tipoLixo()).getCodigo();
        this.quantidade = coletaCreateDTO.quantidade();
        this.participacao = participacao;
        this.pontuacao = pontuacao;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoLixo getTipoLixo() {
        return TipoLixo.toEnum(tipoLixo);
    }

    public void setTipoLixo(TipoLixo tipoLixo) {
        this.tipoLixo = tipoLixo.getCodigo();
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(BigDecimal quantidade) {
        this.quantidade = quantidade;
    }

    public BigDecimal getPontuacao() {
        return pontuacao;
    }

    public void setPontuacao(BigDecimal pontuacao) {
        this.pontuacao = pontuacao;
    }

    public Participacao getParticipacao() {
        return participacao;
    }

    public void setParticipacao(Participacao participacao) {
        this.participacao = participacao;
    }

    public Coleta(Long id, TipoLixo tipoLixo, BigDecimal quantidade, BigDecimal pontuacao) {
        this.id = id;
        this.tipoLixo = tipoLixo == null ? null : tipoLixo.getCodigo();
        this.quantidade = quantidade;
        this.pontuacao = pontuacao;
    }
}
