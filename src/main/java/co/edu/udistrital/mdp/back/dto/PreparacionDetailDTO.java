package co.edu.udistrital.mdp.back.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
@Data

public class PreparacionDetailDTO extends PreparacionDTO{
    private List <UtensiliosDTO> preparaciones = new ArrayList<>();
    private List <IngredientePreparacionDTO> fotos = new ArrayList<>();
}
