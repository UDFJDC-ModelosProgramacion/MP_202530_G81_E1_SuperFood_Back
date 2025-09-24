package co.edu.udistrital.mdp.back.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Entity
@Data
public class ServicioEntity  extends BaseEntity{
    private String nombre;
    private String descripcion;
    private Double costo;
    private String categoria;
    
    //asociaciones OneToMany con las entidades Foto y Comentario
    //asociacion OneToOne con la entidad evento
    @PodamExclude
    @OneToMany(mappedBy = "servicio")
    private List<FotoEntity> fotos;

    @PodamExclude
    @OneToMany(mappedBy = "servicio")
    private List<ComentarioEntity> comentarios;

    @PodamExclude
    @OneToMany(mappedBy = "servicios")
    private ProfesionalEntity profesional;

    @PodamExclude
    @OneToOne(mappedBy = "servicio")
    private EventoEntity evento;
}
