package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data

public class EstrellasMichelinEntity extends BaseEntity {

    private String descripcion;
    private Integer CantidadEstrellas;
     
}
