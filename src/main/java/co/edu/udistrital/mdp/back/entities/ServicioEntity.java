package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import jakarta.persistence.ManyToOne;

@Entity
@Data
public class ServicioEntity  extends BaseEntity{
    private String nombre;
    private String descripcion;
    private Double costo;
    private String categoria;
    
    @PodamExclude
    @ManyToOne

    private ProfesionalEntity profesional;
}
