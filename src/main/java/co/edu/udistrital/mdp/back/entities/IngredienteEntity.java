
package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

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

    @podamExclude;
    @onetoMany(mappedBy = "ingrediente")
    private List <FotoEntity> fotos = new arrayList<>();

    @podamExclude;
    @ManyToMany()
    private PreparacionEntity preparaciones;

}
