package co.edu.udistrital.mdp.back.dto;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

public class ChefAficionadoDTO {

    private static final Logger logger = Logger.getLogger(ChefAficionadoDTO.class.getName());

    private String experiencia;
    private String mejorPlatillo;
    private PerfilDTO perfil;            
    private List<RecetaDTO> recetas = new ArrayList<>();    

    public RecetaDTO crearReceta() {
        logger.info("Creando nueva receta...");
        return new RecetaDTO();
    }

    public RecetaDTO editarReceta(RecetaDTO receta) {
        logger.info("Editando receta: " + (receta != null ? receta.getNombre() : "null"));
        return receta;
    }

    public RecetaDTO eliminarReceta(RecetaDTO receta) {
        logger.info("Eliminando receta: " + (receta != null ? receta.getNombre() : "null"));
        return receta;
    }

    public String getExperiencia() { return experiencia; }
    public void setExperiencia(String experiencia) { this.experiencia = experiencia; }

    public String getMejorPlatillo() { return mejorPlatillo; }
    public void setMejorPlatillo(String mejorPlatillo) { this.mejorPlatillo = mejorPlatillo; }

    public PerfilDTO getPerfil() { return perfil; }
    public void setPerfil(PerfilDTO perfil) { this.perfil = perfil; }

    public List<RecetaDTO> getRecetas() { return recetas; }
    public void setRecetas(List<RecetaDTO> recetas) { this.recetas = recetas; }
}
