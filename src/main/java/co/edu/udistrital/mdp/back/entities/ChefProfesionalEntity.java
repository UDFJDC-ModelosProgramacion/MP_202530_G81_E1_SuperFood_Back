package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;

@Data
@Entity
public class ChefProfesionalEntity extends BaseEntity {

    private String especialidad;
    private boolean verificado;

    @PodamExclude
    @OneToMany(mappedBy = "chefProfesional")
    private List<ServicioEntity> servicios = new ArrayList<>();

    @PodamExclude
    @ManyToMany(mappedBy = "chefs")
    private List<RestauranteEntity> restaurantes = new ArrayList<>();

}
