package com.gs.ecoocean.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "areas")
@Getter
@Setter
@NoArgsConstructor
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cep;
    private String cidade;
    private String estado;
    private String rua;
    private String descricao;
    private String latitudex;
    private String latitudey;

    @OneToMany(mappedBy = "area")
    private List<Partida> partidas;

    public Area(Long id,String cep,String cidade,String estado,String rua,String descricao,String latitudex,String latitudey){
        this.id = id;
        this.cep = cep;
        this.cidade = cidade;
        this.estado = estado;
        this.rua = rua;
        this.descricao = descricao;
        this.latitudex = latitudex;
        this.latitudey = latitudey;
    }
}
