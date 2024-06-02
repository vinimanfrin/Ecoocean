package com.gs.ecoocean.model;

import com.gs.ecoocean.dto.participacao.ParticipacaoCreateDTO;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "participacoes")
@Data
@NoArgsConstructor
public class Participacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "voluntario_id")
    private Voluntario voluntario;

    @ManyToOne
    @JoinColumn(name = "partida_id")
    private Partida partida;

    @OneToMany(mappedBy = "participacao")
    private List<Coleta> coletas;

    private Integer pontuacao;

    public Participacao(Voluntario voluntario, Partida partida) {
        this.voluntario = voluntario;
        this.partida = partida;
        this.pontuacao = 0;
    }
}
