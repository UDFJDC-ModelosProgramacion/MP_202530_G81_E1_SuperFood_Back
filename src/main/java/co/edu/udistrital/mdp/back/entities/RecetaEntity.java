package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class RecetaEntity  extends BaseEntity{
    
    String nombre;
    String descripcion;
    Integer tiempoPreparacion;
    Integer porciones;
    
    @PodamExclude;
    @OneToOne(mappedBy "receta");

    private PreparacionEntity preparacion;

    @podamExclude;
    @ManyToMany(mappedBy "recetas");

    private List <FotosEntity> fotos = new ArrayList<>();

    @podamExclude;
    @oneToMany(mappedBy "receta");

    private List <ComentarioEntity> comentarios = new ArrayList<>(); // esta clase no extiste la persona responsable de comentario entity debe cambiarle el nombre

    @podamExclude;
    @ManyToOne(mappedBy "preparaciones"); 

    private ChefAficionadoEntity chefAficionado;







}