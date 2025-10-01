package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
    @OneToOne(mappedBy = "receta")

    private PreparacionEntity preparacion;

    @PodamExclude
    @ManyToMany(mappedBy = "recetas")

    private List <FotoEntity> fotos = new ArrayList<>();

    @PodamExclude
    @OneToMany(mappedBy = "receta")

    private List <ComentarioEntity> comentarios = new ArrayList<>(); // esta clase no extiste la persona responsable de comentario entity debe cambiarle el nombre

    @PodamExclude
    @ManyToOne

    private ChefAficionadoEntity chefAficionado;







}