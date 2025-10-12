package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;
import java.util.ArrayList;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class PerfilEntity extends BaseEntity {
    
    private String descripcion;
    private String comidaPreferida;

    @PodamExclude
    @OneToMany(mappedBy = "perfil")
    private List<FotoEntity> fotos = new ArrayList<>();
    
}
