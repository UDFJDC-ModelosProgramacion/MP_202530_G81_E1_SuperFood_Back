
package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
public class EventoEntity extends BaseEntity {

    String fecha; 

    @PodamExclude
    @OneToOne
    private ComensalEntity comensal;

    @PodamExclude
    @OneToOne
    private ServicioEntity servicio;
}

