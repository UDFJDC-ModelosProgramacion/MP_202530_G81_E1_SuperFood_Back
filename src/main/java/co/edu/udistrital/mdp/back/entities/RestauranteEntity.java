package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class RestauranteEntity extends BaseEntity {

    private String nombre;
    private String horario;
    private String horarios;
    private Integer porciones;

    //Many-to-Many con ChefProfesional
    @PodamExclude
    @ManyToMany
    private List<ChefProfesionalEntity> chefs = new ArrayList<>();

    //One-to-Many con Foto
    @PodamExclude
    @OneToMany(mappedBy = "restaurante")
    private List<FotoEntity> fotos = new ArrayList<>();

    //One-to-One con Ubicacion
    @PodamExclude
    @OneToOne
    private UbicacionRestauranteEntity ubicacion;

    //One-to-One con EstrellasMichelin
    @PodamExclude
    @OneToOne
    private EstrellasMichelinEntity estrellasMichelin;
}
