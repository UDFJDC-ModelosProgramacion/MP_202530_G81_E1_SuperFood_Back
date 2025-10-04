
package co.edu.udistrital.mdp.back.entities;

import org.hibernate.annotations.ManyToAny;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class RestauranteEntity extends BaseEntity {

    private String nombre;
    private String horario;
    private String ubicacion;
    private String categoria;
    private Integer porciones;

    @PodamExclude
    @ManyToMany
    
    private ChefProfesionalEntity chefProfesionales;
}
