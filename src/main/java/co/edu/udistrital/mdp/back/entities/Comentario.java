package co.edu.udistrital.mdp.back.entities;
import jakarta.persistence.Entity;
import lombok.Data;
@Entity
@Data
public class Comentario extends BaseEntity{
    private Integer calificacion;
    private String descripcion;
    private String nobreUsuario;
}
