package co.edu.udistrital.mdp.back.dto;

import lombok.Data;

enum CategoriaServicio {
    CATERING,
    CHEF_A_DOMICILIO,
    CLASES_DE_COCINA,
    CONSULTORIA_GASTRONOMICA
}

@Data
public class ServicioDTO {
    String nombre;
    String descripcion;
    double costo;
    CategoriaServicio categoria;
}
