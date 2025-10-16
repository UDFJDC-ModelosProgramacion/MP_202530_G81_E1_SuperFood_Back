package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;
import java.util.List;
import java.util.ArrayList;


@Data
@Entity
public class UsuarioEntity extends BaseEntity {
    private String nombre;
    private String email;

    @PodamExclude
    @ManyToMany()
    private List<EventoEntity> eventos = new ArrayList<>();

    @PodamExclude
    @OneToOne(mappedBy = "usuario")
    private PerfilEntity perfil;

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
