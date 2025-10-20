package co.edu.udistrital.mdp.back.dto;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
public class EventoDetailDTO extends EventoDTO {

    private List<String> asistentes = new ArrayList<>();
    
}
