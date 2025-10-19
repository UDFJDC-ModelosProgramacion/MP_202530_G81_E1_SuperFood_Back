package co.edu.udistrital.mdp.back.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class FotoEntity extends BaseEntity {
    private String enlace;
    private String nombre;

    //asociaciones ManyToOne con las entidades Servicio, Receta, Restaurante e Ingrediente

    @PodamExclude
    @ManyToOne
    private ServicioEntity servicio;

    @PodamExclude
    @ManyToOne
    private RecetaEntity receta;

    @PodamExclude
    @ManyToOne
    private RestauranteEntity restaurante;

    @PodamExclude
    @ManyToOne  
    private IngredienteEntity ingrediente;

    @PodamExclude
    @ManyToOne
    private PerfilEntity perfil;
}
