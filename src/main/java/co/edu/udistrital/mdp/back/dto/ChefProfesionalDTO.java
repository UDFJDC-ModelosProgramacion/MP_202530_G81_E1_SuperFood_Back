package co.edu.udistrital.mdp.back.dto;

import java.util.List;
import java.util.ArrayList;

public class ChefProfesionalDTO {

    private boolean verificado;
    private List<ServicioDTO> servicios = new ArrayList<>();   
    private List<RestauranteDTO> restaurantes = new ArrayList<>(); 
    private PerfilDTO perfil;              

    public boolean verificacionCuenta() {
        return verificado;
    }

    public ServicioDTO crearServicio() {
        return new ServicioDTO();
    }

    public ServicioDTO gestionarServicio() {
        return new ServicioDTO();
    }

    public void eliminarServicio(ServicioDTO servicio) {
    }

    public void borrarComentario() {
    }

    public void mostrarComentario() {
    }

    public boolean isVerificado() { return verificado; }
    public void setVerificado(boolean verificado) { this.verificado = verificado; }

    public List<ServicioDTO> getServicios() { return servicios; }
    public void setServicios(List<ServicioDTO> servicios) { this.servicios = servicios; }

    public List<RestauranteDTO> getRestaurantes() { return restaurantes; }
    public void setRestaurantes(List<RestauranteDTO> restaurantes) { this.restaurantes = restaurantes; }

    public PerfilDTO getPerfil() { return perfil; }
    public void setPerfil(PerfilDTO perfil) { this.perfil = perfil; }
}
