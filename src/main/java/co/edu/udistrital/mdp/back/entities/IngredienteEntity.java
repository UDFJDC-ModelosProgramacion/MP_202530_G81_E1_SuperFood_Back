
package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;


@Data
@Entity

public class IngredienteEntity  extends BaseEntity{

    String nombre;
    Boolean esProteina;
    Boolean esGrasa;
    Boolean esCarbohidrato;

    @PodamExclude
    @OneToMany(mappedBy = "ingrediente")
    private List <FotoEntity> fotos = new ArrayList<>();

    @PodamExclude
    @ManyToMany()
    private List <PreparacionEntity>  preparaciones = new ArrayList<>();

}
