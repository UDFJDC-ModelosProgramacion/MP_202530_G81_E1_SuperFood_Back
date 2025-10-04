package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity

public class ProfesionalEntity extends ChefEntity {
    private boolean verificado;

    @PodamExclude
    @OneToMany (mappedBy = "profesional")

    private List<ServicioEntity> servicios = new ArrayList<>();

    @PodamExclude
    @OneToMany (mappedBy = "profesional")

    private List<RestauranteEntity> restaurantes = new ArrayList<>();
}
