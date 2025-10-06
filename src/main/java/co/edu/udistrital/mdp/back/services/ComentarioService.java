package co.edu.udistrital.mdp.back.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.back.entities.ComentarioEntity;
import co.edu.udistrital.mdp.back.entities.ServicioEntity;
import co.edu.udistrital.mdp.back.repositories.ComentarioRepository;

@Service
public class ComentarioService {
    @Autowired
    private ComentarioRepository comentarioRepository;

    //Crear comentario
    public ComentarioEntity crearComentario(ComentarioEntity comentario) {
        return comentarioRepository.save(comentario);
    }
    //Obtener todos los comentarios
    public List<ComentarioEntity> obtenerComentarios() {
        return comentarioRepository.findAll();
    }
    //Obtener comentario por id
    public ComentarioEntity obtenerComentarioPorId(Long id) {
        return comentarioRepository.findById(id).orElse(null);
    }
    //Actualizar comentario
    public ComentarioEntity actualizarComentario(Long id, ComentarioEntity comentarioActualizado) {
        ComentarioEntity comentario = comentarioRepository.findById(id).orElse(null);
        if (comentario != null) {
            comentario.setDescripcion(comentarioActualizado.getDescripcion());
            comentario.setCalificacion(comentarioActualizado.getCalificacion());
            return comentarioRepository.save(comentario);
        }
        return null;
    }
    //Eliminar comentario
    public boolean eliminarComentario(Long id) {
        ComentarioEntity comentario = comentarioRepository.findById(id).orElse(null);
        if (comentario != null) {
            comentarioRepository.delete(comentario);
            return true;
        }
        return false;
    }
    //Obtener comentarios por id de servicio
    public List<ComentarioEntity> obtenerComentariosPorServicioId(ServicioEntity servicio) {
        if (servicio != null) {
            return comentarioRepository.findByServicio(servicio);
        }else {
            return null;
        }
    }
}
