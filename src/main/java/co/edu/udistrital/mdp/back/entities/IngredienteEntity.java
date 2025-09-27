
package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class IngredienteEntity  extends BaseEntity{

    String nombre;
    String categoria;

    @podamExclude;
    @onetoMany(mappedBy = "ingrediente")
    private List <FotoEntity> fotos = new arrayList<>();

    @podamExclude;
    @manyToOne()
    private PreparacionEntity preparacion;

}
