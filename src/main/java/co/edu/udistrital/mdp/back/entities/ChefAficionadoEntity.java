package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class ChefAficionadoEntity extends BaseEntity {

    private String experiencia;
    private String mejorPlatillo;

    @PodamExclude
    @OneToMany(mappedBy = "chefAficionado")
    private List<RecetaEntity> recetas = new ArrayList<>();

}
