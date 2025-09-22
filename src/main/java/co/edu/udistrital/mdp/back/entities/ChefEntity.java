package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class ChefEntity extends BaseEntity {
    private String nombre;
    private String email;
  
}