package co.edu.udistrital.mdp.back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udistrital.mdp.back.entities.ComentarioEntity;
import co.edu.udistrital.mdp.back.entities.ServicioEntity;
import co.edu.udistrital.mdp.back.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.back.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.back.repositories.ComentarioRepository;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    //Crear comentario
    @Transactional
    public ComentarioEntity crearComentario(ComentarioEntity comentario) throws IllegalOperationException {
        // Validar calificación (debe estar entre 1 y 5)
        if (comentario.getCalificacion() == null || comentario.getCalificacion() < 1 || comentario.getCalificacion() > 5) {
            throw new IllegalOperationException("La calificación debe estar entre 1 y 5");
        }
        
        // Validar nombre de usuario
        if (comentario.getNombreUsuario() == null || comentario.getNombreUsuario().trim().isEmpty()) {
            throw new IllegalOperationException("El nombre de usuario no puede ser nulo o vacío");
        }
        
        // Validar que tenga servicio o receta (al menos uno)
        if (comentario.getServicio() == null && comentario.getReceta() == null) {
            throw new IllegalOperationException("El comentario debe estar asociado a un servicio o una receta");
        }
        
        return comentarioRepository.save(comentario);
    }
    
    //Obtener todos los comentarios
    public List<ComentarioEntity> obtenerComentarios() {
        return comentarioRepository.findAll();
    }
    
    //Obtener comentario por id
    public ComentarioEntity obtenerComentarioPorId(Long id) throws EntityNotFoundException, IllegalOperationException {
        if (id == null) {
            throw new IllegalOperationException("El ID no puede ser nulo");
        }
        
        ComentarioEntity comentario = comentarioRepository.findById(id).orElse(null);
        if (comentario == null) {
            throw new EntityNotFoundException("Comentario no encontrado con id: " + id);
        }
        
        return comentario;
    }
    
    //Actualizar comentario
    @Transactional
    public ComentarioEntity actualizarComentario(Long id, ComentarioEntity comentarioActualizado) throws EntityNotFoundException, IllegalOperationException {
        if (id == null) {
            throw new IllegalOperationException("El ID no puede ser nulo");
        }
        
        ComentarioEntity comentario = comentarioRepository.findById(id).orElse(null);
        if (comentario == null) {
            throw new EntityNotFoundException("Comentario no encontrado con id: " + id);
        }
        
        // Validar calificación (debe estar entre 1 y 5)
        if (comentarioActualizado.getCalificacion() == null || comentarioActualizado.getCalificacion() < 1 || comentarioActualizado.getCalificacion() > 5) {
            throw new IllegalOperationException("La calificación debe estar entre 1 y 5");
        }
        
        // Validar nombre de usuario
        if (comentarioActualizado.getNombreUsuario() == null || comentarioActualizado.getNombreUsuario().trim().isEmpty()) {
            throw new IllegalOperationException("El nombre de usuario no puede ser nulo o vacío");
        }
        
        // Validar que tenga servicio o receta (al menos uno)
        if (comentarioActualizado.getServicio() == null && comentarioActualizado.getReceta() == null) {
            throw new IllegalOperationException("El comentario debe estar asociado a un servicio o una receta");
        }
        
        comentario.setDescripcion(comentarioActualizado.getDescripcion());
        comentario.setCalificacion(comentarioActualizado.getCalificacion());
        comentario.setNombreUsuario(comentarioActualizado.getNombreUsuario());
        comentario.setServicio(comentarioActualizado.getServicio());
        comentario.setReceta(comentarioActualizado.getReceta());
        
        return comentarioRepository.save(comentario);
    }
    
    //Eliminar comentario
    @Transactional
    public boolean eliminarComentario(Long id) throws EntityNotFoundException, IllegalOperationException {
        if (id == null) {
            throw new IllegalOperationException("El ID no puede ser nulo");
        }
        
        ComentarioEntity comentario = comentarioRepository.findById(id).orElse(null);
        if (comentario == null) {
            throw new EntityNotFoundException("Comentario no encontrado con id: " + id);
        }
        
        comentarioRepository.delete(comentario);
        return true;
    }
    
    //Obtener comentarios por id de servicio
    public List<ComentarioEntity> obtenerComentariosPorServicioId(ServicioEntity servicio) {
        if (servicio != null) {
            return comentarioRepository.findByServicio(servicio);
        } else {
            return null;
        }
    }
}
