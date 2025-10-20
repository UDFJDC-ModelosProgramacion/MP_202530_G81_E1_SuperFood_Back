package co.edu.udistrital.mdp.back.dto;

import lombok.Data;

@Data
public class IngredienteDTO {

    private String nombre;
    private Boolean esProteina;
    private Boolean esGrasa;
    private Boolean esCarbohidrato;
}
