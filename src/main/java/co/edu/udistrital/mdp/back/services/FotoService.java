package co.edu.udistrital.mdp.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.FotoEntity;
import co.edu.udistrital.mdp.back.repositories.FotoRepository;

@Service
public class FotoService {
    @Autowired
    private FotoRepository fotoRepository;
    
    //crear foto
    public FotoEntity crearFoto(FotoEntity foto) {
        return fotoRepository.save(foto);
    }
    //obtener foto por enlce
    public FotoEntity obtenerFotoPorEnlace(String enlace) {
        return fotoRepository.findByEnlace(enlace);
    }
    //eliminar foto
    public boolean eliminarFoto(Long id) {
        FotoEntity foto = fotoRepository.findById(id).orElse(null);
        if (foto != null) {
            fotoRepository.delete(foto);
            return true;
        }
        return false;
    }
    //obtener fotos por servicio
    public java.util.List<FotoEntity> obtenerFotosPorServicio(co.edu.udistrital.mdp.back.entities.ServicioEntity servicio) {
        if (servicio != null) {
            return fotoRepository.findByServicio(servicio);
        }else {
            return null;
        }
    }
    //obtener fotos por restaurante
    public java.util.List<FotoEntity> obtenerFotosPorRestaurante(co.edu.udistrital.mdp.back.entities.RestauranteEntity restaurante) {
        if (restaurante != null) {
            return fotoRepository.findByRestaurante(restaurante);
        }else {
            return null;
        }
    }
    //obtener fotos por receta
    public java.util.List<FotoEntity> obtenerFotosPorReceta(co.edu.udistrital.mdp.back.entities.RecetaEntity receta) {
        if (receta != null) {
            return fotoRepository.findByReceta(receta);
        }else {
            return null;
        }
    }
    //obtener fotos por ingrediente
    public java.util.List<FotoEntity> obtenerFotosPorIngrediente(co.edu.udistrital.mdp.back.entities.IngredienteEntity ingrediente) {
        if (ingrediente != null) {
            return fotoRepository.findByIngrediente(ingrediente);
        }else {
            return null;
        }
    }
    //obtener todas las fotos
    public java.util.List<FotoEntity> obtenerTodasLasFotos() {
        return fotoRepository.findAll();
    }
}
