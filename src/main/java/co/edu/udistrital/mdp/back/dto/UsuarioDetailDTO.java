package co.edu.udistrital.mdp.back.dto;

import java.util.List;
import java.util.ArrayList;

import lombok.Data;

@Data
public class UsuarioDetailDTO extends UsuarioDTO {
    
    private PerfilDTO perfil;
    private List<EventoDTO> eventos = new ArrayList<>();
}
