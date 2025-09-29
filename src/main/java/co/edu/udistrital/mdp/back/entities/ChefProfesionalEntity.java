package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class ChefProfesionalEntity extends BaseEntity {
    private boolean verificado;
}
