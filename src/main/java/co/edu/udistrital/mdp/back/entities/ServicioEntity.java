package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Entity
@Data
public class ServicioEntity  extends BaseEntity{
    String nombre;
    String descripcion;
    Double costo;
    String categoria;
    

}
