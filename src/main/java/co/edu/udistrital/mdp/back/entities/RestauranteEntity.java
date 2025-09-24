
package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class RestauranteEntity extends BaseEntity {

    private String nombre;
    private String horario;
    private String ubicacion;
    private String categoria;
    private Integer porciones;

}
