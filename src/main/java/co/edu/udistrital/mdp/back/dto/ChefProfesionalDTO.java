package co.edu.udistrital.mdp.back.dto;

import java.util.List;
import java.util.ArrayList;

import lombok.Data;

@Data
public class ChefProfesionalDTO {

    private boolean verificado;
    private List<ServicioDTO> servicios = new ArrayList<>();   
    //private List<RestauranteDTO> restaurantes = new ArrayList<>(); 
    private PerfilDTO perfil;              
}
