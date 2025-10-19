package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;  

@Data
@Entity

public class RecetaEntity  extends BaseEntity{
    
    String nombre;
    String descripcion;
    Integer tiempoPreparacion;
    Integer porciones;
    
    @PodamExclude
    @OneToMany(mappedBy = "receta")

    private List <PreparacionEntity> preparaciones = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "receta")
    private List <FotoEntity> fotos = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "receta")

    private List <ComentarioEntity> comentarios = new ArrayList<>();

    @PodamExclude
    @ManyToOne
    private ChefAficionadoEntity chefAficionado;



}