package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;
import uk.co.jemos.podam.common.PodamExclude;

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

    public String getMarca() {
        return marca;
    }

    public List<PreparacionEntity> getPreparaciones() {
        return preparaciones;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setPreparaciones(List<PreparacionEntity> preparaciones) {
        this.preparaciones = preparaciones;
    }
}
