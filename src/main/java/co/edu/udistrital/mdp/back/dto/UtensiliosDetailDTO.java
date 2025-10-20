package co.edu.udistrital.mdp.back.dto;
import java.util.List;

public class UtensiliosDetailDTO extends UtensiliosDTO {

    private List<PreparacionDetailDTO> preparacionesDetalle; 

    public List<PreparacionDetailDTO> getPreparacionesDetalle() { return preparacionesDetalle; }
    public void setPreparacionesDetalle(List<PreparacionDetailDTO> preparacionesDetalle) { 
        this.preparacionesDetalle = preparacionesDetalle; 
    }
}
