package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity

public class ChefProfesionalEntity extends ChefAficionadoEntity {
    private boolean verificado;

    @PodamExclude
    @OneToMany(mappedBy = "chefProfesional")

    private List<ServicioEntity> servicios = new ArrayList<>();

    @PodamExclude
    @ManyToMany(mappedBy = "chefProfesionales")

    private List<RestauranteEntity> restaurantes = new ArrayList<>();
}
