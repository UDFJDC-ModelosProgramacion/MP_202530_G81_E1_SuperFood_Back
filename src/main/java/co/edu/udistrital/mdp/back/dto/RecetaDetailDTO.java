package co.edu.udistrital.mdp.back.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data

public class RecetaDetailDTO extends RecetaDTO{
    private List <PreparacionDTO> preparaciones = new ArrayList<>();
    private List <FotoDTO> fotos = new ArrayList<>();
    private List <ComentarioDTO> comentarios = new ArrayList<>();
}
