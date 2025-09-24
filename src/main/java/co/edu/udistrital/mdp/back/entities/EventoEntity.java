
package co.edu.udistrital.mdp.back.entities;

import jakarta.persistence.Entity;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
public class EventoEntity extends BaseEntity {

    private String nombre;          // Nombre del evento
    private String descripcion;     // Descripción general
    private String ubicacion;       // Dirección o lugar
    private LocalDateTime fecha;    // Fecha y hora del evento

}
