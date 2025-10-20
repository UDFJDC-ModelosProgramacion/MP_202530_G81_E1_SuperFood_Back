package co.edu.udistrital.mdp.back.dto;

import java.util.List;
import java.util.ArrayList;
//import co.edu.udistrital.mdp.back.dto.RestauranteDetailDTO;

import lombok.Data;
@Data
public class ChefProfesionalDetailDTO extends ChefProfesionalDTO {

    private List<String> certificaciones = new ArrayList<>();
    private int anosExperiencia;
    private String biografia;
    private List<UtensiliosDetailDTO> utensilios = new ArrayList<>();
    private List<ServicioDetailDTO> serviciosDetail = new ArrayList<>();
    //private List<RestauranteDetailDTO> restaurantesDetail = new ArrayList<>();
    private PerfilDTO perfilDetail;

}
