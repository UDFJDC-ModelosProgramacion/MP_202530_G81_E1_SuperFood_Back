package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class AficionadoEntity extends ChefEntity {
    private String descripcionExperiencia;
}
