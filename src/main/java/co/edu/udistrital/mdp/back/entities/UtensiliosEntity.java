package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class UtensiliosEntity extends BaseEntity {

    private String nombre;
    private String marca;

    @PodamExclude
    @ManyToMany(mappedBy = "utensilios")
    private List<PreparacionEntity> preparaciones = new ArrayList<>();

}
