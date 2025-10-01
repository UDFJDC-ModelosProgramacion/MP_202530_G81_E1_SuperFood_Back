package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class ChefAficionadoEntity extends BaseEntity {
    private String descripcionExperiencia;
    private String mejorPlatillo;
}
