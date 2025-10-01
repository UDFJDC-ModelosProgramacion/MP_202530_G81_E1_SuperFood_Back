package co.edu.udistrital.mdp.back.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
@Entity
@Data
public class ComentarioEntity extends BaseEntity{
    private Integer calificacion;
    private String descripcion;
    private String nombreUsuario;

    //asociaciones ManyToOne con las entidades Servicio y Receta
    @PodamExclude
    @ManyToOne
    private ServicioEntity servicio;

    @PodamExclude
    @ManyToOne
    private RecetaEntity receta;

}
