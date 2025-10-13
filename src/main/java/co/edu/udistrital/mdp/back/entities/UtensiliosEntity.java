package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;
import java.util.ArrayList;
import java.util.List;

@Entity
public class UtensiliosEntity extends BaseEntity {

    private String nombre;
    private String marca;

    @PodamExclude
    @ManyToMany(mappedBy = "utensilios")
    private List<PreparacionEntity> preparaciones = new ArrayList<>();

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public List<PreparacionEntity> getPreparaciones() {
        return preparaciones;
    }

    public void setPreparaciones(List<PreparacionEntity> preparaciones) {
        this.preparaciones = preparaciones;
    }

    public void setId(long l) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public Long getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
