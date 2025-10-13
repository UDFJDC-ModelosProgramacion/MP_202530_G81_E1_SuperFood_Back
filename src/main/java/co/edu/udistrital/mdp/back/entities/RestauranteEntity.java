package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.*;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class RestauranteEntity extends BaseEntity {

    @Column(nullable = false)
    private String nombre;

    private String horario;
    private String horarios;

    @Column(nullable = false)
    private Integer porciones;

    // Many-to-Many con ChefProfesional
    @PodamExclude
    @ManyToMany
    @JoinTable(
        name = "restaurante_chef",
        joinColumns = @JoinColumn(name = "restaurante_id"),
        inverseJoinColumns = @JoinColumn(name = "chef_id")
    )
    private Set<ChefProfesionalEntity> chefs = new HashSet<>();

    // One-to-Many con Foto
    @PodamExclude
    @OneToMany(mappedBy = "restaurante", fetch = FetchType.LAZY)
    private List<FotoEntity> fotos = new ArrayList<>();

    // One-to-One con Ubicacion
    @PodamExclude
    @OneToOne
    @JoinColumn(name = "ubicacion_id", unique = true)
    private UbicacionRestauranteEntity ubicacion;

    // One-to-One con EstrellasMichelin
    @PodamExclude
    @OneToOne
    @JoinColumn(name = "estrellas_id", unique = true)
    private EstrellasMichelinEntity estrellasMichelin;

    /* ---- Convenience methods opcionales ---- */
    public void addChef(ChefProfesionalEntity c) {
        if (c != null) {
            this.chefs.add(c);
            c.getRestaurantes().add(this);
        }
    }
    public void removeChef(ChefProfesionalEntity c) {
        if (c != null) {
            this.chefs.remove(c);
            c.getRestaurantes().remove(this);
        }
    }
    public void addFoto(FotoEntity f) {
        if (f != null) {
            this.fotos.add(f);
            f.setRestaurante(this);
        }
    }
    public void removeFoto(FotoEntity f) {
        if (f != null) {
            this.fotos.remove(f);
            f.setRestaurante(null);
        }
    }
}
