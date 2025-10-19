package co.edu.udistrital.mdp.back.dto;

import java.util.List;
import java.util.ArrayList;
import java.util.logging.Logger;

public class UtensiliosDTO {

    private static final Logger logger = Logger.getLogger(UtensiliosDTO.class.getName());

    private String nombre;
    private List<PreparacionDTO> preparaciones = new ArrayList<>(); 

    public void mostrarUtensilio() {
    logger.log(java.util.logging.Level.INFO, "Utensilio: {0}", nombre);
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public List<PreparacionDTO> getPreparaciones() { return preparaciones; }
    public void setPreparaciones(List<PreparacionDTO> preparaciones) { this.preparaciones = preparaciones; }

}
