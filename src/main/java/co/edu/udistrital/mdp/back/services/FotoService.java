package co.edu.udistrital.mdp.back.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.FotoEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.repositories.FotoRepository;

@Service
public class FotoService {
    @Autowired
    private FotoRepository fotoRepository;
    
    //crear foto
    public FotoEntity crearFoto(FotoEntity foto) throws IllegalOperationException {
        // Validar que el enlace no sea null o vacío
        if (foto.getEnlace() == null || foto.getEnlace().trim().isEmpty()) {
            throw new IllegalOperationException("El enlace de la foto no puede ser nulo o vacío");
        }
        
        // Validar que todas las entidades requeridas estén presentes
        if (foto.getServicio() == null) {
            throw new IllegalOperationException("El servicio no puede ser nulo");
        }
        if (foto.getReceta() == null) {
            throw new IllegalOperationException("La receta no puede ser nula");
        }
        if (foto.getRestaurante() == null) {
            throw new IllegalOperationException("El restaurante no puede ser nulo");
        }
        if (foto.getIngrediente() == null) {
            throw new IllegalOperationException("El ingrediente no puede ser nulo");
        }
        if (foto.getPerfil() == null) {
            throw new IllegalOperationException("El perfil no puede ser nulo");
        }
        
        return fotoRepository.save(foto);
    }
    
    //obtener foto por enlce
    public FotoEntity obtenerFotoPorEnlace(String enlace) {
        return fotoRepository.findByEnlace(enlace);
    }
    
    //eliminar foto
    public boolean eliminarFoto(Long id) throws IllegalOperationException, EntityNotFoundException {
        if (id == null) {
            throw new IllegalOperationException("El ID no puede ser nulo");
        }
        
        FotoEntity foto = fotoRepository.findById(id).orElse(null);
        if (foto == null) {
            throw new EntityNotFoundException("Foto no encontrada con id: " + id);
        }
        
        fotoRepository.delete(foto);
        return true;
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
    
    //obtener foto por id
    public FotoEntity obtenerFotoPorId(Long id) throws IllegalOperationException, EntityNotFoundException {
        if (id == null) {
            throw new IllegalOperationException("El ID no puede ser nulo");
        }
        
        FotoEntity foto = fotoRepository.findById(id).orElse(null);
        if (foto == null) {
            throw new EntityNotFoundException("Foto no encontrada con id: " + id);
        }
        
        return foto;
    }
    
    //actualizar foto
    public FotoEntity actualizarFoto(Long id, FotoEntity foto) throws IllegalOperationException, EntityNotFoundException {
        if (id == null) {
            throw new IllegalOperationException("El ID no puede ser nulo");
        }
        
        FotoEntity fotoActual = fotoRepository.findById(id).orElse(null);
        if (fotoActual == null) {
            throw new EntityNotFoundException("Foto no encontrada con id: " + id);
        }
        
        // Validar que el enlace no sea null o vacío
        if (foto.getEnlace() == null || foto.getEnlace().trim().isEmpty()) {
            throw new IllegalOperationException("El enlace de la foto no puede ser nulo o vacío");
        }
        
        // Validar que todas las entidades requeridas estén presentes
        if (foto.getServicio() == null) {
            throw new IllegalOperationException("El servicio no puede ser nulo");
        }
        if (foto.getReceta() == null) {
            throw new IllegalOperationException("La receta no puede ser nula");
        }
        if (foto.getRestaurante() == null) {
            throw new IllegalOperationException("El restaurante no puede ser nulo");
        }
        if (foto.getIngrediente() == null) {
            throw new IllegalOperationException("El ingrediente no puede ser nulo");
        }
        if (foto.getPerfil() == null) {
            throw new IllegalOperationException("El perfil no puede ser nulo");
        }
        
        fotoActual.setEnlace(foto.getEnlace());
        fotoActual.setNombre(foto.getNombre());
        fotoActual.setServicio(foto.getServicio());
        fotoActual.setRestaurante(foto.getRestaurante());
        fotoActual.setReceta(foto.getReceta());
        fotoActual.setIngrediente(foto.getIngrediente());
        fotoActual.setPerfil(foto.getPerfil());
        
        return fotoRepository.save(fotoActual);
    }
}
