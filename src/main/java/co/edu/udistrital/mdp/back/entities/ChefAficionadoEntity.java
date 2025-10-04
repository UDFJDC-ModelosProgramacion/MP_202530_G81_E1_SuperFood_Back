package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
<<<<<<< HEAD:src/main/java/co/edu/udistrital/mdp/back/entities/AficionadoEntity.java

public class AficionadoEntity extends ChefEntity {
=======
public class ChefAficionadoEntity extends BaseEntity {
>>>>>>> 6f09c1c (Remove unused entity classes: AficionadoEntity, ChefEntity, ComensalEntity, ProfesionalEntity, UsuarioEntity; add ChefAficionadoEntity, ChefProfesionalEntity, EstrellasMichelinEntity, UbicacionRestaurante, and UtensilioEntity with updated fields.):src/main/java/co/edu/udistrital/mdp/back/entities/ChefAficionadoEntity.java
    private String descripcionExperiencia;
    private String mejorPlatillo;
}
