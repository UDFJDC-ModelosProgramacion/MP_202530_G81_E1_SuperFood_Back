
package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity

public class PreparacionEntity  extends BaseEntity{

    String descripcionPasos;

    @PodamExclude
    @OneToOne

    private RecetaEntity receta;

    @PodamExclude
    @ManyToMany(mappedBy = "preparaciones")

    private List <UtensilioEntity> utensilios = new ArrayList<>();

    @PodamExclude
    @ManyToMany(mappedBy = "preparaciones")

    private List <IngredienteEntity> ingredientes = new ArrayList<>();



}
