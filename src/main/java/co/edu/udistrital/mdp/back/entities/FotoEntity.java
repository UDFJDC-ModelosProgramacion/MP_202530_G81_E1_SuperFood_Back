package co.edu.udistrital.mdp.back.entities;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class FotoEntity extends BaseEntity {
    private String enlace;
    private String nombre; 
}
