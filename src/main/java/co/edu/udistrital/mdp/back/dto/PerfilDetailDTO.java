package co.edu.udistrital.mdp.back.dto;

import java.util.List;

import lombok.Data;

@Data
public class PerfilDetailDTO extends PerfilDTO {
    
    private Long id;
    private UsuarioDTO usuario;
    private List<FotoDTO> fotos;
}