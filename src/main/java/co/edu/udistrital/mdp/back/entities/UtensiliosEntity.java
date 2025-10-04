package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data


public class UtensiliosEntity extends BaseEntity{
    private String nombre;
    
    @PodamExclude
    @ManyToMany(mappedBy = "utensilios")

    private List<PreparacionEntity> preparaciones = new ArrayList<>();

}
