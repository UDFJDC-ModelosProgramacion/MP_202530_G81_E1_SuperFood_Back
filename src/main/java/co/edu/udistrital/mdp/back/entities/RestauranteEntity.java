
package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
public class RestauranteEntity extends BaseEntity {

    private String nombre;
    private String horario;    
    private String horarios;   
    private Integer porciones;

   // === Many-to-Many con ChefProfesional ===
    @PodamExclude
    @ManyToMany
    @JoinTable(
        name = "restaurante_chef",
        joinColumns = @JoinColumn(name = "restaurante_id"),
        inverseJoinColumns = @JoinColumn(name = "chef_id")
    )
    private Set<ChefProfesionalEntity> chefs = new HashSet<>();

    // 1..* Fotos del restaurante
    @PodamExclude
    @OneToMany(mappedBy = "restaurante")
    private List<FotoEntity> fotos;

    // One-to-One con UbicacionRestaurante (propietario: Restaurante)
    @PodamExclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ubicacion_id", unique = true)
    private UbicacionRestauranteEntity ubicacion;

    // One-to-One con EstrellasMichelin (propietario: Restaurante)
    @PodamExclude
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "estrellas_michelin_id", unique = true)
    private EstrellasMichelinEntity estrellasMichelin;
    
}