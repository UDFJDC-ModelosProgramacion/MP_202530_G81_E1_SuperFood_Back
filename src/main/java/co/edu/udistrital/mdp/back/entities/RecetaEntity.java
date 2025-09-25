package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class RecetaEntity  extends BaseEntity{
    
    String nombre;
    String descripcion;
    Integer tiempoPreparacion;
    Integer porciones;

    private ChefEntity chef;
    
}
