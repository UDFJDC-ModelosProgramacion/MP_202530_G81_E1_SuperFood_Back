package co.edu.udistrital.mdp.back.entities;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;


@Entity
@Data
public class IngredientePreparacionEntity extends BaseEntity {

    private double gramaje;
    private String porcion;

    @ManyToOne
    private PreparacionEntity preparacion;

    @ManyToOne
    private IngredienteEntity ingrediente;
}
