package co.edu.udistrital.mdp.back.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data

public class IngredienteDetailDTO extends IngredienteDTO{
    private List <FotoDTO> preparaciones = new ArrayList<>();
    private List <IngredientePreparacionDTO> fotos = new ArrayList<>();
}