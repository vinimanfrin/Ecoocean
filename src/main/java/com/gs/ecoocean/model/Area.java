package com.gs.ecoocean.model;

import com.gs.ecoocean.dto.area.AreaCreateDTO;
import com.gs.ecoocean.dto.area.AreaUpdateDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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
    private List<Partida> partidas = new ArrayList<>();

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

    public Area(AreaCreateDTO areaDTO) {
        this.cep = areaDTO.cep();
        this.cidade = areaDTO.cidade();
        this.estado = areaDTO.estado();
        this.rua = areaDTO.rua();
        this.descricao = areaDTO.descricao();
        this.latitudex = areaDTO.latitudex();
        this.latitudey = areaDTO.latitudey();
    }

    public void update(AreaUpdateDTO areaUpdateDTO){
        this.cep = areaUpdateDTO.cep();
        this.cidade = areaUpdateDTO.cidade();
        this.estado = areaUpdateDTO.estado();
        this.rua = areaUpdateDTO.rua();
        this.descricao = areaUpdateDTO.descricao();
        this.latitudex = areaUpdateDTO.latitudex();
        this.latitudey = areaUpdateDTO.latitudey();
    }
}
