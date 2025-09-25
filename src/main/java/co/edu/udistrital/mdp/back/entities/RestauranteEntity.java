package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.List;

@Data
@Entity
public class RestauranteEntity extends BaseEntity {

    private String nombre;
    private String horario;
    private String ubicacion;
    private String categoria;
    private int porciones;

    @PodamExclude
    @ManyToOne

    private ProfesionalEntity profesional;

    @PodamExclude
    @OneToMany(mappedBy = "restaurante")

    private List<FotoEntity> fotos;
}

