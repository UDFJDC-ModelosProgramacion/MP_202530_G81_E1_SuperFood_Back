package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;

@Data
@Entity
@EqualsAndHashCode(callSuper = true)

public class ChefEntity extends UsuarioEntity {

    @PodamExclude
    @OneToMany(mappedBy = "chef")

    private List<RecetaEntity> recetas = new ArrayList<>();
}