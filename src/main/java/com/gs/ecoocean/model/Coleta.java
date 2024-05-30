package com.gs.ecoocean.model;

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
    private Integer quantidade;

    @ManyToOne
    @JoinColumn(name = "participacao_id")
    private Participacao participacao;

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

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Coleta(Long id, TipoLixo tipoLixo, Integer quantidade) {
        this.id = id;
        this.tipoLixo = tipoLixo == null ? null : tipoLixo.getCodigo();
        this.quantidade = quantidade;
    }
}
