package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class PerfilEntity extends BaseEntity {
    private String descripcion;
    private String comidaPreferida;
    private String foto;
    
}
