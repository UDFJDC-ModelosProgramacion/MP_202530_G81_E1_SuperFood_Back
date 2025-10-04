package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
<<<<<<< HEAD:src/main/java/co/edu/udistrital/mdp/back/entities/UsuarioEntity.java

public class UsuarioEntity extends BaseEntity {
    private String nombre;
    private String email; 
=======
@Data


public class UtensilioEntity extends BaseEntity{
    private String nombre;

>>>>>>> 6f09c1c (Remove unused entity classes: AficionadoEntity, ChefEntity, ComensalEntity, ProfesionalEntity, UsuarioEntity; add ChefAficionadoEntity, ChefProfesionalEntity, EstrellasMichelinEntity, UbicacionRestaurante, and UtensilioEntity with updated fields.):src/main/java/co/edu/udistrital/mdp/back/entities/UtensilioEntity.java
}
