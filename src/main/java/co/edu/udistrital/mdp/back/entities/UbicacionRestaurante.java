package co.edu.udistrital.mdp.back.entities;
import jakarta.persistence.Entity;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;

@Entity
<<<<<<< HEAD:src/main/java/co/edu/udistrital/mdp/back/entities/ChefEntity.java
@EqualsAndHashCode(callSuper = true)

public class ChefEntity extends UsuarioEntity {

    @PodamExclude
    @OneToMany(mappedBy = "chef")

    private List<RecetaEntity> recetas = new ArrayList<>();
}
=======
@Data


public class UbicacionRestaurante extends BaseEntity{
    private String direccion;
}
>>>>>>> 6f09c1c (Remove unused entity classes: AficionadoEntity, ChefEntity, ComensalEntity, ProfesionalEntity, UsuarioEntity; add ChefAficionadoEntity, ChefProfesionalEntity, EstrellasMichelinEntity, UbicacionRestaurante, and UtensilioEntity with updated fields.):src/main/java/co/edu/udistrital/mdp/back/entities/UbicacionRestaurante.java
