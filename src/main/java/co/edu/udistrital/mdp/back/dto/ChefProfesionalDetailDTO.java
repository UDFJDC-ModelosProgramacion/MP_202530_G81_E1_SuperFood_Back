package co.edu.udistrital.mdp.back.dto;

import java.util.List;
import java.util.ArrayList;
import co.edu.udistrital.mdp.back.dto.UtensiliosDetailDTO;
import co.edu.udistrital.mdp.back.dto.ServicioDetailDTO;
import co.edu.udistrital.mdp.back.dto.RestauranteDetailDTO;
import co.edu.udistrital.mdp.back.dto.PerfilDTO;

public class ChefProfesionalDetailDTO extends ChefProfesionalDTO {

    private List<String> certificaciones = new ArrayList<>();
    private int anosExperiencia;
    private String biografia;
    private List<UtensiliosDetailDTO> utensilios = new ArrayList<>();
    private List<ServicioDetailDTO> serviciosDetail = new ArrayList<>();
    private List<RestauranteDetailDTO> restaurantesDetail = new ArrayList<>();
    private PerfilDTO perfilDetail;

    public List<String> getCertificaciones() { return certificaciones; }
    public void setCertificaciones(List<String> certificaciones) { this.certificaciones = certificaciones; }

    public int getAnosExperiencia() { return anosExperiencia; }
    public void setAniosExperiencia(int anosExperiencia) { this.anosExperiencia = anosExperiencia; }

    public String getBiografia() { return biografia; }
    public void setBiografia(String biografia) { this.biografia = biografia; }

    public List<UtensiliosDetailDTO> getUtensilios() { return utensilios; }
    public void setUtensilios(List<UtensiliosDetailDTO> utensilios) { this.utensilios = utensilios; }

    public List<ServicioDetailDTO> getServiciosDetail() { return serviciosDetail; }
    public void setServiciosDetail(List<ServicioDetailDTO> serviciosDetail) { this.serviciosDetail = serviciosDetail; }

    public List<RestauranteDetailDTO> getRestaurantesDetail() { return restaurantesDetail; }
    public void setRestaurantesDetail(List<RestauranteDetailDTO> restaurantesDetail) { this.restaurantesDetail = restaurantesDetail; }

    public PerfilDTO getPerfilDetail() { return perfilDetail; }
    public void setPerfilDetail(PerfilDTO perfilDetail) { this.perfilDetail = perfilDetail; }

    public void agregarUtensilio(UtensiliosDetailDTO u) {
        if (this.utensilios == null) this.utensilios = new ArrayList<>();
        this.utensilios.add(u);
    }

    public void agregarServicioDetail(ServicioDetailDTO s) {
        if (this.serviciosDetail == null) this.serviciosDetail = new ArrayList<>();
        this.serviciosDetail.add(s);
    }
}
