
package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

enum CategoriaIngredienteEnum {
    PROTEINA,
    GRASA,
    CARBOHIDRATO
}

@Data
@Entity

public class IngredienteEntity  extends BaseEntity{

    String nombre;
    CategoriaIngredienteEnum categoria;

    @PodamExclude
    @OneToMany(mappedBy = "ingrediente")
    private List <FotoEntity> fotos = new ArrayList<>();

    @PodamExclude
    @ManyToMany()
    private List <PreparacionEntity>  preparaciones = new ArrayList<>();

}
