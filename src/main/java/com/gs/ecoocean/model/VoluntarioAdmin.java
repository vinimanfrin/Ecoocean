package com.gs.ecoocean.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Table(name = "voluntarios_administradores")
@Data
@NoArgsConstructor
public class VoluntarioAdmin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String email;

    @OneToMany(mappedBy = "voluntarioAdmin")
    private List<Partida> partidas;
}
