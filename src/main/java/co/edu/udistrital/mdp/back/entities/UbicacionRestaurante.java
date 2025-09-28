package co.edu.udistrital.mdp.back.entities;
import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data


public class UbicacionRestaurante extends BaseEntity{
    private String direccion;
}
