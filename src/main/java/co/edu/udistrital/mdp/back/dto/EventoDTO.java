package co.edu.udistrital.mdp.back.dto;
import java.util.Date;
import lombok.Data;


@Data
public class EventoDTO {
    //nombre fecha ubicacion descripcion
    private String nombre;
    private Date fecha;
    private String ubicacion;
    private String descripcion;
}
