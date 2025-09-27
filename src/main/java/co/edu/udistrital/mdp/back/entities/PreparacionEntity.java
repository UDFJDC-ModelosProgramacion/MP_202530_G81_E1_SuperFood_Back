
package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class PreparacionEntity  extends BaseEntity{

    String descripcionPasos;

    @PodamExclude;
    @OneToOne();

    private RecetaEntity receta;

    @PodamExclude;
    @ManyToMany(mappedBy "utensilio");

    private List <UtensiliosEntity> utensilios = new ArrayList<>();

    @PodamExclude;
    @OneToMany(mappedBy "ingrediente");

    private List <IngredienteEntity> ingredientes = new ArrayList<>();



}
