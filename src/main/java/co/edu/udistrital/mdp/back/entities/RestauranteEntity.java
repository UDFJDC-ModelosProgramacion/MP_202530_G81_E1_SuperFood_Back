
package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.List;

@Data
@Entity
public class RestauranteEntity extends BaseEntity {

    private String nombre;
    private String horario;
    private String ubicacion;
    private String categoria;
    private Integer porciones;

    
    @PodamExclude
    @ManyToOne
    private Profesional profesional;

    
    @PodamExclude
    @OneToMany(mappedBy = "restaurante")
    private List<FotoEntity> fotos;
}
