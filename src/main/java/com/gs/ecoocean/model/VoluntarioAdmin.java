package com.gs.ecoocean.model;

import com.gs.ecoocean.dto.voladmin.VoluntarioAdminCreateDTO;
import com.gs.ecoocean.dto.voladmin.VoluntarioAdminUpdateDTO;
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

    @OneToOne
    @JoinColumn(name = "auth_id")
    private Auth auth;

    public VoluntarioAdmin(VoluntarioAdminCreateDTO voluntarioAdminDTO, Auth auth) {
        this.nome = voluntarioAdminDTO.nome();
        this.email = voluntarioAdminDTO.email();
        this.auth = auth;
    }

    public void update(VoluntarioAdminUpdateDTO voluntarioAdminUpdateDTO) {
        this.nome = voluntarioAdminUpdateDTO.nome();
        this.email = voluntarioAdminUpdateDTO.email();
    }
}
