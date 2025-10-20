package co.edu.udistrital.mdp.back.dto;

import java.util.List;

import lombok.Data;

@Data
public class ServicioDetailDTO extends ServicioDTO {
    private List<ComentarioDTO> comentarios;
    private List<FotoDTO> fotos;
}
