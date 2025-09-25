package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity

public class UsuarioEntity extends BaseEntity {
    private String nombre;
    private String email; 
}
